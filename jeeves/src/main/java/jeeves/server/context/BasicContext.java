//=============================================================================
//===	Copyright (C) 2001-2005 Food and Agriculture Organization of the
//===	United Nations (FAO-UN), United Nations World Food Programme (WFP)
//===	and United Nations Environment Programme (UNEP)
//===
//===	This library is free software; you can redistribute it and/or
//===	modify it under the terms of the GNU Lesser General Public
//===	License as published by the Free Software Foundation; either
//===	version 2.1 of the License, or (at your option) any later version.
//===
//===	This library is distributed in the hope that it will be useful,
//===	but WITHOUT ANY WARRANTY; without even the implied warranty of
//===	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//===	Lesser General Public License for more details.
//===
//===	You should have received a copy of the GNU Lesser General Public
//===	License along with this library; if not, write to the Free Software
//===	Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//===
//===	Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
//===	Rome - Italy. email: GeoNetwork@fao.org
//==============================================================================

package jeeves.server.context;

import jeeves.config.springutil.JeevesApplicationContext;
import org.fao.geonet.Logger;
import jeeves.monitor.MonitorManager;
import org.fao.geonet.utils.Log;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.Map;

//=============================================================================

/** Contains a minimun context for a job execution (schedule, service etc...)
  */

public class BasicContext implements Logger
{

	protected Logger logger = Log.createLogger(Log.JEEVES);
	private   String baseUrl;
	private   String appPath;

	protected Map<String, Object> htContexts;
    private MonitorManager monitorManager;
    private final JeevesApplicationContext jeevesApplicationContext;

    //--------------------------------------------------------------------------
	//---
	//--- Constructor
	//---
	//--------------------------------------------------------------------------

	public BasicContext(JeevesApplicationContext jeevesApplicationContext, MonitorManager mm, Map<String, Object> contexts)
	{

		this.jeevesApplicationContext = jeevesApplicationContext;
        this.monitorManager = mm;
		htContexts = Collections.unmodifiableMap(contexts);
	}

	//--------------------------------------------------------------------------
	//---
	//--- API methods
	//---
	//--------------------------------------------------------------------------


    public Logger getLogger()  { return logger;  }

	//--- read/write objects
	public String getBaseUrl() { return baseUrl; }
	public String getAppPath() { return appPath; }

	//--------------------------------------------------------------------------

	public void setBaseUrl(String name) { baseUrl = name; }
	public void setAppPath(String path) { appPath = path; }

	//--------------------------------------------------------------------------

	public Object getHandlerContext(String contextName)
	{
		return htContexts.get(contextName);
	}

    //--------------------------------------------------------------------------

    public MonitorManager getMonitorManager() {
        return monitorManager;
    }

    //--------------------------------------------------------------------------

    public JeevesApplicationContext getApplicationContext() {
        return jeevesApplicationContext;
    }
    //--------------------------------------------------------------------------
    
    public <T> T getBean(Class<T> beanType) {
        return jeevesApplicationContext.getBean(beanType);
    }

	//--------------------------------------------------------------------------

    @Override
    public boolean isDebugEnabled() { return logger.isDebugEnabled(); }
    @Override
	public void debug(final String message) { logger.debug(message); }
    @Override
	public void info(final String message) { logger.info(message); }
    @Override
	public void warning(final String message) { logger.warning(message); }
    @Override
	public void error(final String message) { logger.error(message); }
    @Override
    public void fatal(final String message) { logger.fatal(message); }
    @Override
    public String getModule() { return logger.getModule(); }

    public <T> T executeInTransaction(TransactionCallback<T> callback) {
        final TransactionTemplate template = new TransactionTemplate(getBean(JpaTransactionManager.class));
        return template.execute(callback);
    }

    public void executeInTransaction(TransactionCallbackWithoutResult callback) {
        executeInTransaction((TransactionCallback)callback);
    }
}

//=============================================================================

