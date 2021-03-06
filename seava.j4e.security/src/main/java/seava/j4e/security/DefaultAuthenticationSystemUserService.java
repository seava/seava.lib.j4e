/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package seava.j4e.security;

import java.util.Date;

import seava.j4e.api.Constants;
import seava.j4e.api.ISettings;
import seava.j4e.api.exceptions.InvalidConfiguration;
import seava.j4e.api.security.IAuthenticationSystemUserService;
import seava.j4e.api.security.ILoginParams;
import seava.j4e.api.security.LoginParamsHolder;
import seava.j4e.api.session.IClient;
import seava.j4e.api.session.ISessionUser;
import seava.j4e.api.session.IUser;
import seava.j4e.api.session.IUserProfile;
import seava.j4e.api.session.IUserSettings;
import seava.j4e.api.session.IWorkspace;
import seava.j4e.commons.security.AppClient;
import seava.j4e.commons.security.SessionUser;
import seava.j4e.commons.security.AppUser;
import seava.j4e.commons.security.AppUserProfile;
import seava.j4e.commons.security.AppUserSettings;
import seava.j4e.commons.security.AppWorkspace;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Authenticates a system user from the {@link SystemAdministratorUsers}
 * 
 * @author amathe
 * 
 */
public class DefaultAuthenticationSystemUserService implements
		IAuthenticationSystemUserService {

	private SystemAdministratorUsers repository;
	private ISettings settings;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		SystemAdministratorUser u = this.repository.findByUserName(username);
		if (u == null) {
			throw new UsernameNotFoundException("Bad credentials");
		}
		ILoginParams lp = LoginParamsHolder.params.get();

		IClient client = new AppClient(null, null, null);
		IUserSettings settings;
		try {
			settings = AppUserSettings.newInstance(this.settings);
		} catch (InvalidConfiguration e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		IUserProfile profile = new AppUserProfile(true, u.getRoles(), false,
				false, false);

		String workspacePath = this.getSettings().get(Constants.PROP_WORKSPACE);

		IWorkspace ws = new AppWorkspace(workspacePath);

		IUser user = new AppUser(u.getCode(), u.getName(), u.getLoginName(),
				u.getPassword(), null, null, client, settings, profile, ws,
				true);

		ISessionUser su = new SessionUser(user, lp.getUserAgent(), new Date(),
				lp.getRemoteHost(), lp.getRemoteIp());
		return su;
	}

	public SystemAdministratorUsers getRepository() {
		return repository;
	}

	public void setRepository(SystemAdministratorUsers repository) {
		this.repository = repository;
	}

	public ISettings getSettings() {
		return settings;
	}

	public void setSettings(ISettings settings) {
		this.settings = settings;
	}

}
