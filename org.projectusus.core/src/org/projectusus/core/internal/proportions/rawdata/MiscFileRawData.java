package org.projectusus.core.internal.proportions.rawdata;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.basis.IMiscFileRawData;
import org.projectusus.core.internal.proportions.model.Hotspot;

public class MiscFileRawData implements IMiscFileRawData {

    private IFile fileOfRawData;
    private WarningsCount yellowCount = new WarningsCount();

    public MiscFileRawData( IFile file ) {
        this.fileOfRawData = file;
    }

    public void setYellowCount( int count ) {
        yellowCount.setWarningsCount( count );
    }

    public int getViolationCount( CodeProportionKind metric ) {
        return yellowCount.getViolationCount( metric );
    }

    public void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        if( metric == CodeProportionKind.CW && metric.isViolatedBy( this ) ) {
            IHotspot hotspot = yellowCount.createHotspot();
            ((Hotspot)hotspot).setFile( fileOfRawData );
            hotspots.add( hotspot );
        }
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        return yellowCount.getOverallMetric( metric );
    }

}
