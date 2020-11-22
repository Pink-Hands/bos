package pers.test.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pers.test.bos.domain.QpWorkordermanage;
import pers.test.bos.service.IWorkordermanageService;
import pers.test.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<QpWorkordermanage> {

	@Autowired
	private IWorkordermanageService workordermanageService;

	public String add() throws IOException {
		String f = "1";
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().printf(f);
		return NONE;
	}
}
