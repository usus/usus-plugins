package org.projectusus.ui.internal.hotspots;

import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CC;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.CW;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.KG;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ML;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterFactory;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.ui.internal.hotspots.pages.CCColumnDesc;
import org.projectusus.ui.internal.hotspots.pages.CWColumnDesc;
import org.projectusus.ui.internal.hotspots.pages.HotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.IHotspotsPage;
import org.projectusus.ui.internal.hotspots.pages.KGColumnDesc;
import org.projectusus.ui.internal.hotspots.pages.MLColumnDesc;

public class PageFactory implements IAdapterFactory {

    private final Map<IsisMetrics, IHotspotsPage> pages;

    public PageFactory() {
        pages = new HashMap<IsisMetrics, IHotspotsPage>();
        initPages();
    }

    // raw type in interface we implement - no chance
    @SuppressWarnings( "unchecked" )
    public Object getAdapter( Object adaptableObject, Class adapterType ) {
        Object result = null;
        if( adapterType == IHotspotsPage.class && adaptableObject instanceof CodeProportion ) {
            CodeProportion codeProportion = (CodeProportion)adaptableObject;
            result = pages.get( codeProportion.getMetric() );
        }
        return result;
    }

    public Class<?>[] getAdapterList() {
        return new Class[] { IHotspotsPage.class };
    }

    // internal
    // ////////

    private void initPages() {
        pages.put( CC, new HotspotsPage( CCColumnDesc.values() ) );
        pages.put( ML, new HotspotsPage( MLColumnDesc.values() ) );
        pages.put( CW, new HotspotsPage( CWColumnDesc.values() ) );
        pages.put( KG, new HotspotsPage( KGColumnDesc.values() ) );
    }
}
