package org.projectusus.ui.internal.hotspots.commands;

import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.ui.internal.DisplayHotspot;
import org.projectusus.ui.util.EditorOpener;

public class OpenHotspotInEditor extends AbstractOpenHotspotHandler<FileHotspot> {

    @Override
    protected void open( ExecutionEvent event, DisplayHotspot<FileHotspot> hotspot ) {
        FileHotspot realHotspot = hotspot.getHotspot();
        ICompilationUnit compilationUnit = extractCompilationUnit( hotspot );
        EditorOpener opener = new EditorOpener();
        if( realHotspot == null || compilationUnit == null ) {
            opener.openEditor( hotspot.getFile() );
        } else {
            opener.openEditorAt( compilationUnit, realHotspot.getSourcePosition() );
        }
    }

    private ICompilationUnit extractCompilationUnit( DisplayHotspot<FileHotspot> hotspot ) {
        try {
            return createCompilationUnitFrom( hotspot.getFile() );
        } catch( Exception ignored ) {
            // The contract says that it returns null when a compilation unit
            // couldn't be loaded (e.g. it's a non-Java file); but actually,
            // the request fires an exception. Well, in any case, we know there
            // is no compilation unit...
            return null;
        }
    }

}
