# Updating to 1.8.9 Guide

There are just a few changes needed in the class Helpers in the package org.silvercatcher.reforged.util
Scroll down to the Square-Helper-Method and remove/comment out the 1.8 part and decomment the 1.8.9 part.
Then add this import: net.minecraft.client.renderer.vertex.DefaultVertexFormats;
Then it should be finished.