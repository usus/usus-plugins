# Usus 0.6.3 Release Notes

## New Features

### Simple checkboxes instead of mysterious sliders

We have replaced the mysterious sliders in the Class Graph and the Package Graph with simple checkboxes that show "only cross-package relations" and "only cyclic dependencies", respectively.

### Removed Usus Cross-Package Graph view

Instead, please use the checkbox in the Usus Class Graph.

### Hotspots view preserves selection after re-calculation

The Hotspots view now preserves the current selection of Hotspots when Usus has finished re-calculating the code proportion.

### Usus project preferences are stored in workspace

The preference whether a project is to be covered by Usus used to be stored in the settings area of the project. This often lead to problems when the project was shared with other developers. We decided it would be better to store this preference in the workspace. This way, every developer can decide whether to use Usus and on which projects on his own.

### Select/deselect multiple projects to be covered by Usus

You can now select multiple projects in the "Projects covered by Usus" view and select "Add to/Remove from Usus projects" instead of clicking every single checkbox.

### Metric values distribution histogram

Inspired by Kent Beck talking about the fact that many metrics are power law distributed, we added a very basic histogram to Usus that visualizes how the values of a code proportion are distributed. To use it, simply activate the "Usus Histogram" view and select a code proportion in the Usus Cockpit.

## Feedback

Please let us know what you think. If anything is not working in a way you'd expect, feel free to open an issue on GitHub: https://github.com/usus/usus-plugins/issues
