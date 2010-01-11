// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.CodeStatistic;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.modelupdate.ICheckpoint;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;


public class Checkpoints2GraphicsConverterTest {

	@Test
	public void empty() {
		Checkpoints2GraphicsConverter converter = create(new ArrayList<ICheckpoint>());
		assertNotNull(converter.get(ACD));
		assertEquals(0, converter.get(ACD).length);
	}

	@Test
	public void singleCheckpoint() {
		ICheckpoint checkpoint = new DummyCheckpoint(42);
		Checkpoints2GraphicsConverter converter = create(asList(checkpoint));
		assertEquals(1, converter.get(ACD).length);
		assertEquals(42.0, converter.get(ACD)[0], 0.0);
	}
	
	@Test
	public void checkpointSeries() {
		List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();
		checkpoints.add(new DummyCheckpoint(11));
		checkpoints.add(new DummyCheckpoint(9));
		checkpoints.add(new DummyCheckpoint(22));
		Checkpoints2GraphicsConverter converter = create(checkpoints);
		assertEquals(3, converter.get(ACD).length);
	}
	
	@Test
	public void checkpointSeriesExactlyMaxSize() {
		List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();
		for (int i = 0; i < 16; i++) {
			checkpoints.add(new DummyCheckpoint(i));
		}
		Checkpoints2GraphicsConverter converter = create(checkpoints);
		assertEquals(16, converter.get(ACD).length);
	}
	
	@Test
	public void checkpointSeriesMoreThanMaxSize() {
		List<ICheckpoint> checkpoints = new ArrayList<ICheckpoint>();
		for (int i = 0; i < 20; i++) {
			checkpoints.add(new DummyCheckpoint(i));
		}
		Checkpoints2GraphicsConverter converter = create(checkpoints);
		assertEquals(16, converter.get(ACD).length);
	}
	
	private Checkpoints2GraphicsConverter create(List<ICheckpoint> checkpoints) {
		return new Checkpoints2GraphicsConverter(checkpoints);
	}
	
	private class DummyCheckpoint implements ICheckpoint {

		private final int[] values;
		private final CodeProportionKind metric;

		DummyCheckpoint(int value){
			this(ACD, new int[]{value});
		}
		
		DummyCheckpoint(CodeProportionKind metric, int...values){
			this.values = values;
			this.metric = metric;
		}
		
		public List<CodeProportion> getEntries() {
			List<CodeProportion> result = new ArrayList<CodeProportion>();
			for (int value : values) {
				result.add(new CodeProportion(metric, 0, new CodeStatistic( metric.getUnit(), value), new ArrayList<IHotspot>()));
			}
			return result;
		}

		public DateTime getTime() {
			return null;
		}

                public CodeProportion findCodeProportion( CodeProportionKind kind ) {
                    return null;
                }
	}
}
