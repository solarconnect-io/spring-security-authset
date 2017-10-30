package io.solarconnect.security.core.auth

import org.slf4j.LoggerFactory
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.security.access.expression.ExpressionUtils
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.util.SimpleMethodInvocation
import java.lang.reflect.Method

class SecurityCheckService(private val expressionHandler: MethodSecurityExpressionHandler) {

//	val log = LoggerFactory.getLogger(SecurityCheckService::class.qualifiedName)

	private class SecurityObject {
		fun triggerCheck() { /*NOP*/
		}
	}

	companion object {
		val log = LoggerFactory.getLogger(SecurityCheckService::class.qualifiedName)!!
		private var triggerCheckMethod: Method? = null
		private var parser: SpelExpressionParser? = null

		init {
			try {
				triggerCheckMethod = SecurityObject::class.java.getMethod("triggerCheck")
			} catch (e: NoSuchMethodException) {
				log.error("no such method", e)
			}
			parser = SpelExpressionParser()
		}
	}

	fun check(securityExpression: String): Boolean {
		if (log.isDebugEnabled) {
			log.debug("Checking security expression [$securityExpression]...")
		}

		val securityObject = SecurityObject()
		//MethodSecurityExpressionHandler expressionHandler = ContextLoader.getCurrentWebApplicationContext().getBean(DefaultMethodSecurityExpressionHandler.class);
		val evaluationContext = expressionHandler.createEvaluationContext(SecurityContextHolder.getContext().authentication, SimpleMethodInvocation(securityObject, triggerCheckMethod))
		val checkResult = ExpressionUtils.evaluateAsBoolean(parser!!.parseExpression(securityExpression), evaluationContext)

		if (log.isDebugEnabled) {
			log.debug("Check result: " + checkResult)
		}

		return checkResult
	}
}