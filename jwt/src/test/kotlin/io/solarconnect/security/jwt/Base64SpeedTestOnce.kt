package io.solarconnect.security.jwt

import org.junit.Test

class Base64SpeedTestOnce {
    internal val header = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"

    @Test
    fun test1(){
        for (i:Int in 1..10000){
            java.util.Base64.getDecoder().decode(header)
        }
    }

    @Test
    fun test2(){
        for (i:Int in 1..10000){
            org.apache.commons.codec.binary.Base64.decodeBase64(header)
        }
    }
}