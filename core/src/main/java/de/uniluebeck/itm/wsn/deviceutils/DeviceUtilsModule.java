/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or        *
 *   promote products derived from this software without specific prior written permission.                           *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/

package de.uniluebeck.itm.wsn.deviceutils;

import com.google.inject.Binder;
import com.google.inject.Module;
import de.uniluebeck.itm.wsn.deviceutils.macreader.DeviceMacReaderModule;
import de.uniluebeck.itm.wsn.deviceutils.macreader.DeviceMacReferenceMap;
import de.uniluebeck.itm.wsn.drivers.factories.DeviceFactory;
import de.uniluebeck.itm.wsn.drivers.factories.DeviceFactoryImpl;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutorService;

public class DeviceUtilsModule implements Module {

	private final ExecutorService executorService;

	private final DeviceMacReferenceMap deviceMacReferenceMap;

	private final boolean use16BitMode;
	
	public DeviceUtilsModule(final ExecutorService executorService, @Nullable DeviceMacReferenceMap deviceMacReferenceMap) {
		this(executorService, deviceMacReferenceMap, true);
	}

	public DeviceUtilsModule(final ExecutorService executorService, @Nullable DeviceMacReferenceMap deviceMacReferenceMap, boolean use16BitMode) {
		this.executorService = executorService;
		this.deviceMacReferenceMap = deviceMacReferenceMap;
		this.use16BitMode = use16BitMode;
	}

	@Override
	public void configure(final Binder binder) {
		binder.install(new DeviceMacReaderModule(executorService, deviceMacReferenceMap, use16BitMode));
		binder.bind(DeviceFactory.class).to(DeviceFactoryImpl.class);
	}
}
