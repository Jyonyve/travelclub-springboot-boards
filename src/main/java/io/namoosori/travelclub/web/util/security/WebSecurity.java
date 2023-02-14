package io.namoosori.travelclub.web.util.security;

import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
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
