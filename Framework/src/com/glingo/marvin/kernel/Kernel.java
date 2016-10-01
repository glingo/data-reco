package com.glingo.marvin.kernel;

import java.util.Date;

import com.glingo.marvin.commons.util.ClassLoaderUtil;
import com.glingo.marvin.router.Router;

public class Kernel {

	private long id;
	protected Router router = new Router();

	public Kernel() {
		super();
		this.id = new Date().getTime() + Math.round(Math.random() * 10);
	}

	public synchronized void registerBundles(String... paths) {

		if (paths == null || paths.length == 0)
			return;

		for (int i = 0; i < paths.length; i++) {
			getRouter().registerBundle(ClassLoaderUtil.loadClass(paths[i]));
		}
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
