package io.namoosori.travelclub.web.util.security;

import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import io.namoosori.travelclub.web.util.exception.NoSuchMemberException;
import lombok.Data;
import lombok.NoArgsConstructor;
import netscape.javascript.JSObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Data
@Component("webSecurity")
@NoArgsConstructor
public class WebSecurity {
    public boolean checkAuthority(String siteRoles) {
        System.out.println("checkAuthority run" + siteRoles);
        try{
            Roles roles = Roles.valueOf(siteRoles);
            if(roles.equals(Roles.ADMIN.getKey())) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
