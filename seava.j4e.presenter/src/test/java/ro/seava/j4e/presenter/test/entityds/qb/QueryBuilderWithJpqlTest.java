package ro.seava.j4e.presenter.test.entityds.qb;

import java.util.ArrayList;
import java.util.List;

import ro.seava.j4e.api.exceptions.InvalidConfiguration;
import ro.seava.j4e.api.session.IClient;
import ro.seava.j4e.api.session.IUser;
import ro.seava.j4e.api.session.IUserProfile;
import ro.seava.j4e.api.session.IUserSettings;
import ro.seava.j4e.api.session.IWorkspace;
import ro.seava.j4e.api.session.Session;
import ro.seava.j4e.commons.action.query.FilterRule;
import ro.seava.j4e.commons.security.AppClient;
import ro.seava.j4e.commons.security.AppUser;
import ro.seava.j4e.commons.security.AppUserProfile;
import ro.seava.j4e.commons.security.AppUserSettings;
import ro.seava.j4e.commons.security.AppWorkspace;
import ro.seava.j4e.presenter.action.query.QueryBuilderWithJpql;
import ro.seava.j4e.presenter.descriptor.DsDescriptor;
import ro.seava.j4e.presenter.descriptor.ViewModelDescriptorManager;

import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;

public class QueryBuilderWithJpqlTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryBuilderWithModelOnly() throws Exception {

		Model filter = new Model();

		IClient client = new AppClient("client-id", "CLIENT-CODE", "Client Name");
		IUserSettings settings;
		try {
			settings = AppUserSettings.newInstance(null);
		} catch (InvalidConfiguration e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		IUserProfile profile = new AppUserProfile(true, null, false, false, false);

		String workspacePath = "";

		IWorkspace ws = new AppWorkspace(workspacePath);

		IUser su = new AppUser("TEST", "Test User", "test", "test", null, null,
				client, settings, profile, ws, true);

		Session.user.set(su);

		try {

			// ==================== prepare ======================

			DsDescriptor<Model> descriptor = ViewModelDescriptorManager
					.getDsDescriptor(Model.class, false);

			@SuppressWarnings("rawtypes")
			QueryBuilderWithJpql qb = new QueryBuilderWithJpql();

			qb.setModelClass(Model.class);
			qb.setFilterClass(Model.class);

			qb.setBaseEql("BASE-EQL");
			qb.setDescriptor(descriptor);

			qb.setDefaultWhere(descriptor.getJpqlDefaultWhere());
			qb.setDefaultSort(descriptor.getJpqlDefaultSort());

			// ================== execute =======================

			filter.setCode("x");
			filter.setSequenceNoAlias(1);

			FilterRule rule;
			List<FilterRule> rules = new ArrayList<FilterRule>();

			// 1
			rule = new FilterRule();
			rule.setFieldName("code");
			rule.setOperation(QueryBuilderWithJpql.OP_EQ);
			rule.setValue1("a");
			rules.add(rule);

			// 2
			rule = new FilterRule();
			rule.setFieldName("code");
			rule.setOperation(QueryBuilderWithJpql.OP_NOT_EQ);
			rule.setValue1("a");
			rules.add(rule);

			// 3
			rule = new FilterRule();
			rule.setFieldName("code");
			rule.setOperation(QueryBuilderWithJpql.OP_LIKE);
			rule.setValue1("a");
			rules.add(rule);

			// 4
			rule = new FilterRule();
			rule.setFieldName("code");
			rule.setOperation(QueryBuilderWithJpql.OP_NOT_LIKE);
			rule.setValue1("a");
			rules.add(rule);

			// 5
			rule = new FilterRule();
			rule.setFieldName("id");
			rule.setOperation(QueryBuilderWithJpql.OP_GT);
			rule.setValue1("1");
			rules.add(rule);

			// 6
			rule = new FilterRule();
			rule.setFieldName("id");
			rule.setOperation(QueryBuilderWithJpql.OP_GT_EQ);
			rule.setValue1("1");
			rules.add(rule);

			// 7
			rule = new FilterRule();
			rule.setFieldName("id");
			rule.setOperation(QueryBuilderWithJpql.OP_LT);
			rule.setValue1("1");
			rules.add(rule);

			// 8
			rule = new FilterRule();
			rule.setFieldName("id");
			rule.setOperation(QueryBuilderWithJpql.OP_LT_EQ);
			rule.setValue1("1");
			rules.add(rule);

			// 9
			rule = new FilterRule();
			rule.setFieldName("id");
			rule.setOperation(QueryBuilderWithJpql.OP_BETWEEN);
			rule.setValue1("1");
			rule.setValue1("2");
			rules.add(rule);

			qb.setFilter(filter);
			qb.setFilterRules(rules);

			String expectedEql = "BASE-EQL join fetch e.parent1 left join fetch e.parent2 where  e.name not null  and e.sequenceNo > :sequenceNoAlias and e.code like :code and e.clientId = :clientId and e.code = :code_1 and e.code <> :code_2 and e.code like :code_3 and e.code not like :code_4 and e.id > :id_5 and e.id >= :id_6 and e.id < :id_7 and e.id <= :id_8 and e.id between :id_9_1 and :id_9_2 order by e.code,e.name desc";
			String resultEql = qb.buildQueryStatement();

			System.out.println("expected: " + expectedEql);
			System.out.println("result: " + resultEql);

			assertEquals(expectedEql, resultEql);
		} finally {
			Session.user.set(null);
		}
	}
}
