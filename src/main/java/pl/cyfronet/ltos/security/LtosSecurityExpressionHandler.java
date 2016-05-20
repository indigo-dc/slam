package pl.cyfronet.ltos.security;

import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class LtosSecurityExpressionHandler extends
		DefaultMethodSecurityExpressionHandler {

	private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	@Override
	public void setReturnObject(Object returnObject, EvaluationContext ctx) {
		((MethodSecurityExpressionRoot) ctx.getRootObject().getValue())
				.setReturnObject(returnObject);
	}

	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
			Authentication authentication,
			org.aopalliance.intercept.MethodInvocation invocation) {
		final MethodSecurityExpressionRoot root = new LtosSecurityExpressionRoot(
				authentication);
		root.setThis(invocation.getThis());
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(this.trustResolver);
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}

}