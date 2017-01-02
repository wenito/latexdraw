package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.*;
import net.sf.latexdraw.util.LNamespace;

import java.text.ParseException;
import java.util.List;

/**
 * Defines a SVG generator for some joined lines.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class LPolylinesSVGGenerator extends LModifiablePointsGenerator<IPolyline> {
	/**
	 * Creates a generator for IPolyline.
	 * @param polyline The source polyline used to generate the SVG element.
	 */
	protected LPolylinesSVGGenerator(final IPolyline polyline) {
		super(polyline);
	}


	/**
	 * Creates some lines using a SVG path.
	 * @param elt The SVG path.
	 */
	protected LPolylinesSVGGenerator(final SVGPathElement elt) {
		super(ShapeFactory.INST.createPolyline());

		if(elt==null || (!elt.isLines() && !elt.isLine()))
			throw new IllegalArgumentException();

		initModifiablePointsShape(elt);
		setSVGParameters(elt);
	}



	/**
	 * Creates some joined-lines from an SVG polyline element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LPolylinesSVGGenerator(final SVGPolyLineElement elt) {
		this(ShapeFactory.INST.createPolyline());

		setSVGModifiablePointsParameters(elt);
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates a line from an SVG line element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LPolylinesSVGGenerator(final SVGLineElement elt) {
		this(ShapeFactory.INST.createPolyline());

		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates some joined-lines from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LPolylinesSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createPolyline());

		final SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGPolyLineElement) && !(elt2 instanceof SVGLineElement))
			throw new IllegalArgumentException();

		if(elt2 instanceof SVGPolyLineElement) {
			setSVGModifiablePointsParameters((SVGPolyLineElement)elt2);
		}else {
			final SVGLineElement lineElt = (SVGLineElement)elt2;
			shape.addPoint(ShapeFactory.INST.createPoint(lineElt.getX1(), lineElt.getY1()));
			shape.addPoint(ShapeFactory.INST.createPoint(lineElt.getX2(), lineElt.getY2()));
		}

		setSVGParameters(elt2);
		setSVGLatexdrawParameters(elt);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));
		final IArrow arrow1 	= shape.getArrowAt(0);
		final IArrow arrow2 	= shape.getArrowAt(-1);
		setSVGArrow(arrow1, elt2.getAttribute(elt2.getUsablePrefix()+SVGAttributes.SVG_MARKER_START), elt2, SVGAttributes.SVG_MARKER_START);
		setSVGArrow(arrow2, elt2.getAttribute(elt2.getUsablePrefix()+SVGAttributes.SVG_MARKER_END), elt2, SVGAttributes.SVG_MARKER_END);
		homogeniseArrows(arrow1, arrow2);

		if(withTransformation)
			applyTransformations(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			return null;

        final SVGElement root 		= new SVGGElement(doc);
		final SVGDefsElement defs 	= doc.getFirstChild().getDefs();
		final StringBuilder points 	= new StringBuilder();
		final List<IPoint> pts		= shape.getPoints();
		SVGPolyLineElement elt;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_JOINED_LINES);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		for(final IPoint pt : pts)
			points.append(pt.getX()).append(',').append(pt.getY()).append(' ');

		final String pointsStr = points.toString();

		if(shape.hasShadow()) {
			final SVGPolyLineElement shad = new SVGPolyLineElement(doc);
			try { shad.setPoints(pointsStr); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);
			setSVGArrow(shape, shad, 0, true, doc, defs);
			setSVGArrow(shape, shad, 1, true, doc, defs);
		}

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE) && shape.isFilled()) {
        	// The background of the borders must be filled is there is a shadow.
    		elt = new SVGPolyLineElement(doc);
    		try { elt.setPoints(pointsStr); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
    		setSVGBorderBackground(elt, root);
        }

		elt = new SVGPolyLineElement(doc);
		try { elt.setPoints(pointsStr); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
		root.appendChild(elt);

		if(shape.hasDbleBord()) {
			final SVGPolyLineElement dblBord = new SVGPolyLineElement(doc);
			try { dblBord.setPoints(pointsStr); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		setSVGArrow(shape, elt, 0, false, doc, defs);
		setSVGArrow(shape, elt, shape.getNbArrows()-1, false, doc, defs);

		return root;
	}
}
