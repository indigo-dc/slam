package pl.cyfronet.ltos.security;

import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.TeamMember;
import com.agreemount.bean.identity.provider.IdentityProvider;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class IdentityProviderImpl implements IdentityProvider {
    @Getter
    private final Identity identity = buildIdentity();

    private static Identity buildIdentity() {
        PortalUser portalUser = (PortalUser) SecurityContextHolder.getContext().getAuthentication();
        if (portalUser == null) {
            return null;
        }
        User user = portalUser.getUserBean();
        if (user == null) {
            return null;
        }

        Identity identity = new Identity();
        identity.setLogin(user.getEmail());
        List<String> roles = new ArrayList<>();
        roles.addAll(user.getRoles().stream()
                .map(entry -> entry.getName())
                .collect(Collectors.toList()));
        if (portalUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROVIDER"))) {
            roles.add("provider");
        }
        identity.setRoles(unmodifiableList(roles));
        identity.setTeamMembers(unmodifiableList(
                user.getTeams().stream()
                        .flatMap(team -> team.getRoles().stream()
                                .map(role -> toTeamMember(team, role)))
                        .collect(Collectors.toList())
        ));
        return identity;
    }

    private static TeamMember toTeamMember(Team team, Role role) {
        TeamMember teamMember = new TeamMember();
        teamMember.setRole(role.getName());
        teamMember.setTeam(team.getName());
        return teamMember;
    }
}

