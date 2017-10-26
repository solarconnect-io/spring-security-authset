package io.solarconnect.security.core.config

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class TestBeanConfig {

    @Configuration
    class BaseBeanConfig {
        @Bean
        fun EnvironmentVariablesConfiguration(): EnvironmentStringPBEConfig {
            val conf = EnvironmentStringPBEConfig()
            conf.algorithm = "PBEWithMD5AndDES"
            conf.passwordEnvName = "APP_ENCRYPTION_PASSWORD"
            return conf
        }

        @Bean
        fun ConfigurationEncryptor(): StandardPBEStringEncryptor {
            val encryptor = StandardPBEStringEncryptor()
            encryptor.setConfig(this.EnvironmentVariablesConfiguration())
            encryptor.setPassword("9pwc3dke")
            return encryptor
        }
    }
}