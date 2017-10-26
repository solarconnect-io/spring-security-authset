package io.solarconnect.security.core.rememberme

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class RememberMeUser : UserDetails {

    var username: String
        @JvmName("getUsername_") get() = field
    var series: String
    var tokenValue: String
    var date: Date
    var authorities:MutableCollection<out GrantedAuthority>
        @JvmName("getAuthorities_") get() = field

    constructor(username: String,
                series: String,
                tokenValue: String,
                date: Date) {
        this.username = username
        this.series = series
        this.tokenValue = tokenValue
        this.date = date
        this.authorities = arrayListOf()
    }

    constructor(username: String,
                series: String,
                tokenValue: String,
                date: Date,
                authorities:MutableCollection<out GrantedAuthority>){
        this.username = username
        this.series = series
        this.tokenValue = tokenValue
        this.date = date
        this.authorities = authorities
    }


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}