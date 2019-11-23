package com.happy.springboot.admin;

import com.happy.springboot.common.util.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class SpringbootAdminApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void BCrypt(){
		System.out.println(passwordEncoder.encode("123456"));
	}

//    cityhouse.login.public.key=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4EgG+uRuFpzq/0lBIYyQGhk9tVEWnjJK/xS+x8Sr9rtF/30pVZgfl0UQXgyWITq6N90PzIfij3cqwx3JcTKgJmeI9bN4x9yEws6nGDCuAEWC0wna694UApwo7Lo7BfhS1z4f6174VHsmJ+7RdKWlW3UbZm4I6W9ntDMEpkV95Wema63AkRPrVlb3QC7fK+VBOP0vwinJmF4b285F3becqR/w7iJ+bLIbSCsbaVZ4HfFToz6tG0WEYDRWs5hdjEoZuRg/01zReo3+9m9ypwLXMBJiE7pvuPdNRA4M3qXe/t3z+qzGesgMfZeB6c+ija2xBSAa79J1NHB0SllnKSRrwIDAQAB

	@Test
	public void contextLoads() {
	}

	@Test
	public void StringTest(){
		String test = " ";
		System.out.println(System.currentTimeMillis());
		for (int i = 0; i < 1; i++) {
			StringUtils.isEmpty(test);
		}
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void DateTest(){
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/M/d"));
		System.out.println("当前日期=" + date);
	}

	@Test
	public void PasswordTest(){
	}

	@Test
	public void HashMapTest(){

	}

	@Test
	public void RSATest(){
		try {
			String password = RsaUtils.encryptByPublicKey("123456", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4EgG+uRuFpzq/0lBIYyQGhk9tVEWnjJK/xS+x8Sr9rtF/30pVZgfl0UQXgyWITq6N90PzIfij3cqwx3JcTKgJmeI9bN4x9yEws6nGDCuAEWC0wna694UApwo7Lo7BfhS1z4f6174VHsmJ+7RdKWlW3UbZm4I6W9ntDMEpkV95Wema63AkRPrVlb3QC7fK+VBOP0vwinJmF4b285F3becqR/w7iJ+bLIbSCsbaVZ4HfFToz6tG0WEYDRWs5hdjEoZuRg/01zReo3+9m9ypwLXMBJiE7pvuPdNRA4M3qXe/t3z+qzGesgMfZeB6c+ija2xBSAa79J1NHB0SllnKSRrwIDAQAB",null);
			System.out.println(password);
			String decryptPassword = RsaUtils.decryptByPrivateKey("daVSi9kE8kys7voOHV0tCfHbc++LsHP4/LFKzAoDA2zte2B+zJomemiuUFdYmxMIIq+uGYNIJxc/UZsQ5Jslq16iu4MqZzsN7dhdxBOqRhLuzCUc7e7/187WXzRWo/Hqc1/5hZ71/a78Ak33B48iOqYnJhrCivdeV7XhAZ/ELGOkup874iL4zWIzgX3wum6v4EoL9fLBToIT8+YrUpAZuobWNriQoFNdfdDyd81qLlXs+YNmXS1UFwhW24U/tW32wzsX2tWW9xivF7oBCH9Ia75qgLrI6fLigZqEcQH3D5L3dXDsLrl/DqwQrOuT3Lz7IeaqSjH8V98HvO9OdeXq9Q==","MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrgSAb65G4WnOr/SUEhjJAaGT21URaeMkr/FL7HxKv2u0X/fSlVmB+XRRBeDJYhOro33Q/Mh+KPdyrDHclxMqAmZ4j1s3jH3ITCzqcYMK4ARYLTCdrr3hQCnCjsujsF+FLXPh/rXvhUeyYn7tF0paVbdRtmbgjpb2e0MwSmRX3lZ6ZrrcCRE+tWVvdALt8r5UE4/S/CKcmYXhvbzkXdt5ypH/DuIn5sshtIKxtpVngd8VOjPq0bRYRgNFazmF2MShm5GD/TXNF6jf72b3KnAtcwEmITum+4901EDgzepd7+3fP6rMZ6yAx9l4Hpz6KNrbEFIBrv0nU0cHRKWWcpJGvAgMBAAECggEBAJJJBXZLLHQu0tM0jUqanGpLoTLZHgB9XjpicvU3v9y3w9vfBbmxP4UJJ6ngODFAzo3ra2AsBLMJqRtM+qQAgynF3hl7swmzXL3ZqhiQgAYrBv6D6mvzs4+yF/nJNNuMUuLRXjSpaSz7j+9u73HS+hZjI/qIT4Q4btD0D90dLpuKYcNpEIZvWy/ZtIaxRYJ6clmH3ID7E18+JjJqLBFg9q5cS5uXmn2Dew3oBWXO81BT24LqjILjwAP9kvqOBpyQz4YUs2QzvRR8wgGpwIB3fRNokYUISdCpxhBCb6y4pVIHqgTjI4jwOvwy0FUDnDgNdcaACGHMdmxC1GXud54fxEECgYEA1btxz6Ymxzbx7CmBsq9I9P0Oq4RtAwmKdAp0dWs9E86OICcYKvmSDrEsLK/Bo5oGOW6krnU+SDv4MCsvra6EB1+O4zJ3k7PXlPQMis3MFCx3Mtvjz+h8ShhhR8WWJgnzvsK2XArxlC/s8/qzp6h0fErqdEABuL/YYNLjDAY8kxcCgYEAzWvTjgXtfqXAyrk9b2etydVWYo85A1brsSvXumfbTgrJAXlUmLd8etvoGcE0Gz/oXEVKW+cIirdylDdKbqJmo6mUR70DJo2vsAaFN1ZQ9PV1Hy8qwOpfoVq+b9Z4hRtvOMPe2JZ1fV6cH8UpcJ3Axdu+6H8akX/8EZRTg4i09SkCgYEAxojo7ncrfE/op3Pi22WBgrjygJZxYKnjJ2lyxoOH57cuRGWsSHctmmIvY48bc2omsAC8DqYv5Y93Ot9S2aXLVMIKC7ecZeJftiiA4grKsE6hpuelFUKi+XzuGGpM0CbceO2L0bIEt59RH01YxrtvAOniIXw3XTMvvrkGWOwOJp8CgYB9OtvYvDJhwAi9TZpvXSRsY/tq0Gdwar/63YCBaike9aI+nMn+4/UmLQPdF5R9fENaZP+E8V6aumVGiEJ/GdA6u/nilg7ipQPS6taxupRWEYGaRjXpL7im9FwecAA/HU97qqUjIu5zRIcnMxolRnar6GAE+t+mjgCMcwFKsb+nqQKBgQCf7JhzPlAwaHLrxTRmJgtJr8hvvOn8pGFfgEnmuZ8dIkcEZQ0DPsRHpLBOWdWquKCaYp/xuT4k9SHrlNyHX3X5X2Vet6EUm5UaluqxjEqAfq2idK9JBssHgcZ5zUU7LRjXhrydsGMIiIESRtM+HkMQbH250WyuxalPcWOXZIIkdA==",null);
			System.out.println(decryptPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getRsaPublickKey(){

	}

}
