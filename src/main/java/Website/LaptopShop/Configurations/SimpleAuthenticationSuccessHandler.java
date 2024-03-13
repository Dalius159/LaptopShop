package Website.LaptopShop.Configurations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();

		String targetUrl = getTargetUrl(grantedAuthorities);

		if (response.isCommitted()) {
			logger.debug(
					"Response has already been committed. Unable to redirect to "
							+ targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	public String getTargetUrl(List<GrantedAuthority> grantedAuthorities) {
		int flag = 0;
		for (int i = 0; i < grantedAuthorities.size(); i++) {
			if (grantedAuthorities.get(i).getAuthority().equals("ROLE_ADMIN")) {
				flag = flag | 1; // XOR bit
			}
			if (grantedAuthorities.get(i).getAuthority().equals("ROLE_SHIPPER")) {
				flag = flag | 2;
			}
			if (grantedAuthorities.get(i).getAuthority().equals("ROLE_MEMBER")) {
				flag = flag | 4;
			}
		}

        if (flag == 1 || flag == 3 || flag == 5 || flag == 7) {return "/admin";} else {
            if (flag == 2 || flag == 6) {return "/shipper";} else {return "/";}
        }
	}
}