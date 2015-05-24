package edu.recm.service.common;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 启动Jetty服务器
 * @author niuzhixiang
 *
 */
public class JettyServer {
	
	protected static final int PORT = 9999;
	protected static final String RESOURCE_BASE = "src/main/resources";
	protected static final String CONTEXT_PATH = "/sr";

	public static void main(String[] args) {
		Server server = new Server(PORT);
		WebAppContext context = new WebAppContext();
		context.setDescriptor(JettyServer.class.getClassLoader().getResource("web.xml").toString());
		context.setResourceBase(RESOURCE_BASE);
		context.setClassLoader(Thread.currentThread().getContextClassLoader());
		context.setContextPath(CONTEXT_PATH);
		server.setHandler(context);
		ServletHandler servletHandler = new ServletHandler();
		context.setServletHandler(servletHandler);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
