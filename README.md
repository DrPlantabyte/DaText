DaText
======

DaText: Data as Text format. This is a human-readable data format designed to be intuitive to anyone familiar with JSON, Java, or Javascript. Unlike JSON, it is not intended fo native javascript parsing (although it does have the ability to export to JSON). Instead, it aims for better readability (be removing all those unnecessary quotes), ease of use, and built-in binary conversion (for saving space/bandwidth). Cached extensions of the basic classes buffer the data as native primitives for good performance (and thread safety) during runtime data access.
