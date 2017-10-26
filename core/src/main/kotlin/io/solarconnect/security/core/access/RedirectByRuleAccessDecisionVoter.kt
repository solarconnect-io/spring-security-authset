package io.solarconnect.security.core.access

import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.core.Authentication

class RedirectByRuleAccessDecisionVoter : AccessDecisionVoter<Any>{
    override fun vote(authentication: Authentication?, `object`: Any?, attributes: MutableCollection<ConfigAttribute>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun supports(clazz: Class<*>?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun supports(attribute: ConfigAttribute?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}