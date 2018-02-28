package com.storefront.util;

import static com.storefront.util.ApplicationConstants.CHAR_ENCODING;
import static com.storefront.util.ApplicationConstants.HTTP;
import static com.storefront.util.ApplicationConstants.HTTPS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.storefront.common.StoreFrontException;

@Component
public class HTTPClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HTTPClientUtil.class);

	public String executePostRequest(String url, Map<String, String> headerValues, String requestBody)
			throws StoreFrontException {
		try {
			return this.httpPost(url, headerValues, requestBody);
		} catch (Exception e) {
			logger.error("Error in executing the post request :  " + e);
			throw new StoreFrontException("Error in executing the Request : " + e.getMessage());
		}

	}

	private String httpPost(String url, Map<String, String> headerValues, String requestBody)
			throws StoreFrontException, IOException {
		final SSLConnectionSocketFactory socketFactory;
		CloseableHttpClient httpClient = null;
		String httpResonse = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			headerValues.forEach((key, value) -> httpPost.addHeader(key, value));

			socketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault(), NoopHostnameVerifier.INSTANCE);
			final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, socketFactory).build();

			try (final PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(
					registry)) {
				clientConnectionManager.setMaxTotal(100);
				clientConnectionManager.setDefaultMaxPerRoute(20);
				httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
						.setConnectionManager(clientConnectionManager).build();
				httpPost.setEntity(new ByteArrayEntity(requestBody.getBytes("UTF-8")));
				CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
				httpResonse = this.httpResponseReader(httpResponse);
			}

		} catch (Exception exception) {
			logger.error("Error in executing the post rrequest :  " + exception);
			throw new StoreFrontException(exception.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
		}

		return httpResonse;
	}

	public String executeGetRequest(String url, Map<String, String> headerValues) throws StoreFrontException {
		try {
			return this.httpGet(url, headerValues);
		} catch (Exception exception) {
			logger.error("Error in executing the get request :  " + exception);
			throw new StoreFrontException("Error in executing the Request : " + exception.getMessage());
		}

	}

	private String httpGet(String url, Map<String, String> headerValues) throws StoreFrontException, IOException {
		final SSLConnectionSocketFactory socketFactory;
		CloseableHttpClient httpClient = null;
		String httpGetResonse = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			headerValues.forEach((key, value) -> httpGet.addHeader(key, value));

			socketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault(), NoopHostnameVerifier.INSTANCE);
			final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, socketFactory).build();

			try (final PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager(
					registry)) {
				clientConnectionManager.setMaxTotal(100);
				clientConnectionManager.setDefaultMaxPerRoute(20);
				httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
						.setConnectionManager(clientConnectionManager).build();
				CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

				httpGetResonse = this.httpResponseReader(httpResponse);
			}

		} catch (Exception exception) {
			logger.error("Error in executing the get request :  " + exception);
			throw new StoreFrontException(exception.getMessage());
		}
		return httpGetResonse;
	}

	private String httpResponseReader(CloseableHttpResponse httpResponse) throws StoreFrontException, IOException {
		if (httpResponse != null) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent(), CHAR_ENCODING));
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String httpData = reader.readLine();
				reader.close();
				return httpData;
			} else {
				throw new StoreFrontException("Error in request : " + reader.readLine());
			}
		} else {
			throw new StoreFrontException("Error in receiving the response from host server");
		}

	}

}
