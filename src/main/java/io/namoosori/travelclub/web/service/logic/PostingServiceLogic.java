package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.BoardStore;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.store.MembershipStore;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchBoardException;
import io.namoosori.travelclub.web.util.exception.NoSuchMembershipException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingServiceLogic implements PostingService {

    private static PostingStore postingStore;
    private static BoardStore boardStore;
    private static MembershipStore membershipStore;
    private static PostingService postingServiceLogic;
    private static MemberService memberService;

    private PostingServiceLogic(PostingStore postingStore, BoardStore boardStore, MembershipStore membershipStore) {
        this.postingStore = postingStore;
        this.boardStore = boardStore;
        this.membershipStore = membershipStore;
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public static PostingService getPostingServiceLogic(){
        if (postingServiceLogic == null){
            postingServiceLogic = new PostingServiceLogic(postingStore, boardStore, membershipStore);
        }
        return postingServiceLogic;
    }

    @Override
    public String register(String boardId, PostingCdo postingCdo) {
        Posting posting = null;

        if(boardStore.retrieve(boardId) == null){
            throw new NoSuchBoardException("there are no board with id: "+boardId);
        }
        if(memberService.findAllByRoles(Roles.ADMIN).stream().anyMatch(admin -> admin.getEmail().equals(postingCdo.getWriterEmail()))){
            posting = new Posting(boardId,postingCdo);
            //운영자일 경우 가입없이도 포스팅 가능
        } else if(membershipStore.retrieveByEmail(postingCdo.getWriterEmail()).isEmpty()){
            throw new NoSuchMembershipException("no such member in this club");
        }
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
