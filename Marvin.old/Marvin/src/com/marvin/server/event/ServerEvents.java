/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.server.event;

import com.marvin.console.event.*;
import com.marvin.component.kernel.event.*;

/**
 *
 * @author Dr.Who
 */
public final class ServerEvents {

    public static final String START = "server_start";
    public static final String BEFORE_LOAD = "server_before_load";
    public static final String AFTER_LOAD = "server_after_load";
    public static final String TERMINATE = "server_terminate";
    public static final String ACCEPT = "server_remote_accept";
    public static final String REMOTE_CLOSE = "server_remote_accept";
    
}
