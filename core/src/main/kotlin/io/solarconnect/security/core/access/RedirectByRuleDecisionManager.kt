package io.solarconnect.security.core.access

import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.vote.AbstractAccessDecisionManager
import org.springframework.security.core.Authentication

class RedirectByRuleDecisionManager(decisionVoters: List<AccessDecisionVoter<Any>>) : AbstractAccessDecisionManager(decisionVoters) {
    override fun decide(authentication: Authentication?, `object`: Any?, configAttributes: MutableCollection<ConfigAttribute>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}