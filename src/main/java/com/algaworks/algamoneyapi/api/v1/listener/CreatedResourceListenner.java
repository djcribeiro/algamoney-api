package com.algaworks.algamoneyapi.api.v1.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoneyapi.api.v1.events.CreatedResourceEvent;

@Component
public class CreatedResourceListenner implements ApplicationListener<CreatedResourceEvent> {

	@Override
	public void onApplicationEvent(CreatedResourceEvent createdResourceEvent) {
		HttpServletResponse response = createdResourceEvent.getResponse();
		Long codigo = createdResourceEvent.getId();
		
		
		addHeaderLocation(response, codigo);
	}

	private void addHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
				response.setHeader("Location", uri.toASCIIString());
	}

}
