# Usus 0.7.1 Release Notes


## New Features

### Lack of Cohesion of Classes

This statistic lists packages in which the class connections form two or more unconnected graphs. This usually indicates that the classes in the package do not interact with each other. This may indicate a design problem.

### Mudholes

This statistic lists methods that are of poor quality wrt. multiple aspects. In this statistic, the method length and cyclomatic complexity are considered. If the method is in a large class, this adds to the rating as well.

### Package Size (Per Project)

This statistic lists packages that contain too many classes. If a project contains several source folders that each contain the same package, the class count in all of these packages is added and presented as the package size of this project. If several projects contain the same package, these are regarded as different packages, and the class count is not added.

### Unreferenced Classes

This statistic lists classes that do not have any incoming references from the Java code that is analyzed by Usus. This can mean one or more of the following:

- The class is referenced by code which is located in a project that is not covered by Usus
- The class is referenced by non-Java code, e.g. JSF/JSP, Spring or an OSGi plugin.xml
- The class is a test class
- The class is a contract class
- The class is not referenced anywhere and can thus safely be deleted.

This statistic helps to discover (and subsequently eliminate) code that falls into the last category.

In order to keep the "false positives" as low as possible, the statistic ignores classes whose names end in "Test" or in "Contract".


## Removed Features

We discontinued the BugPrison feature (older versions are still available via the UpdateSite for the time being).
We removed the experimental additions "Nasty Plugin" and "Codereview Plugin" from Usus.


## Bug Fixes

### ACD Calculation

In ACD calculation, the references that stemmed from chained arguments of a chained method invocation were ignored. This problem is resolved.

### Performance Issues

The Class Graph and Package Graph visualizations were very slow, rendering Usus to become unusable on larger projects. In this release, the performance has been massively improved (thanks to the generosity of YourKit who supported us with a free license). Still, if Usus is too slow, you can improve the performance by closing one or both of these windows. 


## Known Problems

### ACD Calculation after the Addition or Removal of a Project

When a project is removed or added, the ACD references may not be established properly. Therefore, after the addition or removal of a project one should always perform a refresh (by pressing the Cockpit icon showing two circular arrows).


## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: https://github.com/usus/usus-plugins/issues
