package seava.j4e.presenter.service.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import seava.j4e.api.Constants;
import seava.j4e.api.enums.SysParam;
import seava.j4e.api.service.IClientInfoProvider;
import seava.j4e.api.service.IPersistableLog;
import seava.j4e.api.service.IPersistableLogService;
import seava.j4e.api.service.PersistableLog;
import seava.j4e.api.service.job.IJob;
import seava.j4e.api.session.IClient;
import seava.j4e.api.session.IUser;
import seava.j4e.api.session.IUserProfile;
import seava.j4e.api.session.IUserSettings;
import seava.j4e.api.session.IWorkspace;
import seava.j4e.api.session.Session;
import seava.j4e.commons.security.AppClient;
import seava.j4e.commons.security.AppUser;
import seava.j4e.commons.security.AppUserProfile;
import seava.j4e.commons.security.AppUserSettings;
import seava.j4e.presenter.service.AbstractPresenterBaseService;

public abstract class AbstractPresenterJob extends AbstractPresenterBaseService
		implements IJob {

	private JobExecutionContext executionContext;
	private IPersistableLog persistableLog;

	final static Logger logger = LoggerFactory
			.getLogger(AbstractPresenterJob.class);

	@Override
	public final void execute() throws Exception {
		try {
			this.beforeExecute();
			this.onExecute();
			this.afterExecute();
		} catch (Exception e) {
			e.printStackTrace();
			throw (e);
		} finally {
			Session.user.set(null);
		}
	}

	private void beforeExecute() throws Exception {

		String clientId = executionContext.getJobDetail().getKey().getGroup();
		String userCode = this.getSettings().getParam(
				SysParam.CORE_JOB_USER.name());

		if (logger.isInfoEnabled()) {
			logger.info("Starting job " + this.getClass().getCanonicalName());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Executing job {}: \n - clientId={} \n - user={} ",
					new Object[] { this.getClass().getCanonicalName(),
							clientId, userCode });
		}

		IClient client = new AppClient(clientId, "", "");
		IUserSettings settings;
		settings = AppUserSettings.newInstance(this.getSettings());
		IUserProfile profile = new AppUserProfile(true, null, false, false, false);

		// create an incomplete user first
		IUser user = new AppUser(userCode, userCode, null, userCode, null, null,
				client, settings, profile, null, true);
		Session.user.set(user);

		// get the client workspace info
		IWorkspace ws = this.getApplicationContext()
				.getBean(IClientInfoProvider.class).getClientWorkspace();
		user = new AppUser(userCode, userCode, null, userCode, null, null, client,
				settings, profile, ws, true);
		Session.user.set(user);

		// ready to proceed ...
		BeanUtils.populate(this, this.executionContext.getJobDetail()
				.getJobDataMap().getWrappedMap());
		this.createPersistableLog();

	}

	protected IPersistableLog getPersistableLog() {
		return this.persistableLog;
	}

	private void createPersistableLog() {
		PersistableLog l = new PersistableLog();

		l.setProperty(PLK_START_TIME, new Date());
		l.setProperty(PLK_JOB_CONTEXT, this.executionContext.getJobDetail()
				.getKey().getName());
		l.setProperty(PLK_JOB_TIMER, this.executionContext.getTrigger()
				.getKey().getName());
		this.persistableLog = l;
	}

	protected abstract void onExecute() throws Exception;

	private void afterExecute() throws Exception {
		try {
			this.persistableLog.setProperty(PLK_END_TIME, new Date());
			for (IPersistableLogService s : getPersistableLogServices()) {
				if (s.getType().equals(IPersistableLogService.PL_TYPE_JOB)) {
					s.insert(persistableLog);
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("Job " + this.getClass().getCanonicalName()
						+ " executed succesfully.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setExecutionContext(Object executionContext) {
		this.executionContext = (JobExecutionContext) executionContext;
	}

	@SuppressWarnings("unchecked")
	public List<IPersistableLogService> getPersistableLogServices() {
		return (List<IPersistableLogService>) this.getApplicationContext()
				.getBean(Constants.SPRING_OSGI_PERSISTABLE_LOG_SERVICES);
	}

}
