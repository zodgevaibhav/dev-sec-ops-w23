package org.dnyanyog.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.dnyanyog.security.EncryptionService;
import org.dnyanyog.security.VZEncrypted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class RequestBodyAdviceAdapterImpl extends RequestBodyAdviceAdapter {

	Logger logger = LoggerFactory.getLogger(RequestBodyAdviceAdapterImpl.class);

	@Autowired
	private EncryptionService encryptionService;

	@Override
	public boolean supports(MethodParameter arg0, Type arg1, Class<? extends HttpMessageConverter<?>> arg2) {
		return true;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		logger.info("*** Processing RequestBodyAdviceAdapterImpl ");
		for (Field field : body.getClass().getDeclaredFields()) {

			if (field.isAnnotationPresent(VZEncrypted.class)) {
				field.setAccessible(true);
				try {
					field.set(body, encryptionService.encrypt(field.get(body).toString()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);

	}
}
