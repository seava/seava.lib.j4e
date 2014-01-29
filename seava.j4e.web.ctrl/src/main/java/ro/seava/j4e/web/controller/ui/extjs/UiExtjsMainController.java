/** 
 * DNet eBusiness Suite
 * Copyright: 2013 Nan21 Electronics SRL. All rights reserved.
 * Use is subject to license terms.
 */
package ro.seava.j4e.web.controller.ui.extjs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.seava.j4e.api.Constants;
import ro.seava.j4e.api.enums.SysParam;
import ro.seava.j4e.api.extensions.IExtensions;
import ro.seava.j4e.api.session.ISessionUser;
import ro.seava.j4e.api.setup.ISetupParticipant;
import ro.seava.j4e.api.setup.IStartupParticipant;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UiExtjsMainController extends AbstractUiExtjsController {

	protected List<ISetupParticipant> setupParticipants;
	protected List<IStartupParticipant> startupParticipants;

	@RequestMapping(value = "*", method = RequestMethod.GET)
	protected ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// if (this.setupParticipants != null) {
		// for (ISetupParticipant sp : setupParticipants) {
		// if (sp.hasWorkToDo()) {
		// response.sendRedirect("/nan21.dnet.core.setup");
		// return null;
		// }
		// }
		// }

		try {
			@SuppressWarnings("unused")
			ISessionUser su = (ISessionUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

		} catch (Exception e) {
			// TODO: parameterize
			response.sendRedirect(this.getSettings().get(
					Constants.PROP_LOGIN_PAGE));
			return null;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		this._prepare(model, request, response);

		/* ========== extensions =========== */

		model.put(
				"extensions",
				getExtensionFiles(IExtensions.UI_EXTJS_MAIN,
						uiExtjsSettings.getUrlCore()));

		model.put("extensionsContent",
				getExtensionContent(IExtensions.UI_EXTJS_MAIN));

		String logo = this.getSettings().getParam(
				SysParam.CORE_LOGO_URL_EXTJS.name());

		if (logo != null && !logo.equals("")) {
			model.put("logo", logo);
		}
		return new ModelAndView(this.viewName, model);
	}

	public List<ISetupParticipant> getSetupParticipants() {
		return setupParticipants;
	}

	public void setSetupParticipants(List<ISetupParticipant> setupParticipants) {
		this.setupParticipants = setupParticipants;
	}

	public List<IStartupParticipant> getStartupParticipants() {
		return startupParticipants;
	}

	public void setStartupParticipants(
			List<IStartupParticipant> startupParticipants) {
		this.startupParticipants = startupParticipants;
	}

}
