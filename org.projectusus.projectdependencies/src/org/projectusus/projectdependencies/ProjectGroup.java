package org.projectusus.projectdependencies;

final class ProjectGroup {

    private String name;

    public ProjectGroup( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals( Object obj ) {
        if( obj instanceof ProjectGroup ) {
            ProjectGroup that = (ProjectGroup)obj;
            return this.name.equals( that.name );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
