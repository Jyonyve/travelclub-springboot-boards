package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.MembershipService;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchMembershipException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostingServiceLogic implements PostingService {

    private static PostingStore postingStore;
    private static MembershipService membershipService;
    private static PostingService postingServiceLogic;
    private static MemberService memberService;

    private PostingServiceLogic(PostingStore postingStore) {
        this.postingStore = postingStore;
        this.membershipService = MembershipServiceLogic.getMembershipServiceLogic();
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public static PostingService getPostingServiceLogic(){
        if (postingServiceLogic == null){
            postingServiceLogic = new PostingServiceLogic(postingStore);
        }
        return postingServiceLogic;
    }

    @Override
    public String register(String boardId, PostingCdo postingCdo) {
        System.out.println(boardId.split("/")[0]);
        System.out.println(postingCdo.getWriterEmail());
        System.out.println(membershipService.isClubMember(boardId.split("/")[0], postingCdo.getWriterEmail()));

        if(memberService.findAllByRoles(Roles.ADMIN).stream().anyMatch(admin -> admin.getEmail().equals(postingCdo.getWriterEmail()))
        || membershipService.isClubMember(boardId.split("/")[0], postingCdo.getWriterEmail())){

            Posting posting = new Posting(boardId,postingCdo);
            //운영자일 경우 가입없이도 포스팅 가능
            //클럽 멤버일 경우 포스팅 가능
            return postingStore.create(posting);
        } else
        {
            throw new NoSuchMembershipException("no such member in this club");
        }

    }

    @Override
    public Posting findById(String postingId) {
        Posting posting = postingStore.retrieve(postingId);
        return posting;
    }

    @Override
    public List<Posting> findByBoardId(String boardId) {
        List<Posting> postings = postingStore.retrieveByBoardId(boardId);
        return postings;
    }

    @Override
    public List<Posting> findBySocialBoardJpo_IdAndWriterEmail(String boardId, String writerEmail) {
        List<Posting> personalPostings = postingStore.retrieveByBoardIdAndWriterEmail(boardId, writerEmail);
        return personalPostings;
    }

    @Override
    public void modify(String postingId, Posting posting) {

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
