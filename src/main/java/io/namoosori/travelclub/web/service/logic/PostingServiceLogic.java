package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.BoardStore;
import io.namoosori.travelclub.web.store.MembershipStore;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchBoardException;
import io.namoosori.travelclub.web.util.exception.NoSuchMembershipException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingServiceLogic implements PostingService {

    private PostingStore postingStore;
    private BoardStore boardStore;
    private MembershipStore membershipStore;

    @Override
    public String register(String boardId, PostingCdo postingCdo) {
        if(boardStore.retrieve(boardId) == null){
            throw new NoSuchBoardException("there are no board with id: "+boardId);
        }
        if(!membershipStore.exists(postingCdo.getWriterEmail())){
            throw new NoSuchMembershipException("no such member in this club");
        }

        Posting posting = new Posting(postingCdo.getTitle(),
                                    postingCdo.getWriterEmail(),
                                    postingCdo.getContents(),
                                    boardId);

        return postingStore.create(posting);
    }

    @Override
    public Posting find(String postingId) {
        Posting posting = postingStore.retrieve(postingId);
        if(posting == null){
            throw new NoSuchPostingException("no posting with the id: "+postingId);
        }
        return posting;
    }

    @Override
    public List<Posting> findByBoardId(String boardId) {
        List<Posting> postings = postingStore.retrieveByBoardId(boardId);
        return postings;
    }

    @Override
    public void modify(String postingId, NameValueList nameValueList) {
        Posting posting = postingStore.retrieve(postingId);
        if(posting == null){
            throw new NoSuchPostingException("No such posting with id: "+postingId);
        }
        posting.modifyValues(nameValueList);
        postingStore.update(posting);

    }

    @Override
    public void remove(String postingId) {
        Posting posting = postingStore.retrieve(postingId);
        if(posting == null){
            throw new NoSuchPostingException("No such posting with id: "+postingId);
        }

        postingStore.delete(postingId);

    }
}
