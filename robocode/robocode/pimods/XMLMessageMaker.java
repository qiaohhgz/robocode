package robocode.pimods;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author Marco Della Vedova - pixelinstrument.net
 * @author Matteo Foppiano - pixelinstrument.net
 * 
 */
public class XMLMessageMaker {

	private boolean currentRound = false;
	private xmlEl currentTurn;
	private xmlEl settings= new xmlEl("settings");

	public XMLMessageMaker() {
		settings= new xmlEl("settings");
	}

	public void addTurn(int numT) {
		currentTurn = new xmlEl("turn");
		currentTurn.params.add(new xmlParam("time", Integer.toString(numT)));
	}
	
	public void setupField( int x, int y ){
		xmlEl field = new xmlEl("field");
		field.addParam( "sizeX", Integer.toString(x) );
		field.addParam( "sizeY", Integer.toString(y) );
		settings.elements.add( field );
	}
	
	public void setupTank(String name, Color body, Color head, Color radar, Color bullet){
		xmlEl tank = new xmlEl("tank");
		tank.addParam("name", name);
		if(body!=null) tank.addParam("body_color", Long.toString(body.getRGB()));
		if(head!=null) tank.addParam("head_color", Long.toString(head.getRGB()));
		if(radar!=null) tank.addParam("radar_color", Long.toString(radar.getRGB()));
		if(bullet!=null) tank.addParam("bullet_color", Long.toString(bullet.getRGB()));
		settings.elements.add(tank);
	}

	public void addTankPosition(String name, double xpos, double ypos,
			double angle, double energy, double headAngle, double radarAngle) {
		xmlEl tank = new xmlEl("tank");
		tank.addParam("name", name);
		tank.addParam("xpos", String.format("%g", xpos));
		tank.addParam("ypos", String.format("%g", ypos));
		tank.addParam("angle", String.format("%g", angle));
		tank.addParam("energy", String.format("%g", energy));
		xmlEl head = new xmlEl("head");
		head.addParam("angle", String.format("%g", headAngle));
		xmlEl radar = new xmlEl("radar");
		radar.addParam("angle", String.format("%g", radarAngle));
		tank.elements.add(head);
		tank.elements.add(radar);
		currentTurn.elements.add(tank);
	}

	public void addBullet(String name, double xpos, double ypos, double power) {
		xmlEl bullet = new xmlEl("bullet");
		bullet.addParam("name", name);
		bullet.addParam("xpos", String.format("%g", xpos));
		bullet.addParam("ypos", String.format("%g", ypos));
		bullet.addParam("power", String.format("%g", power));
		currentTurn.elements.add(bullet);
	}
	public String getCurrentTurn(){
		return currentTurn.toString();
	}
	public String getSettings(){
		return settings.toString();
	}
	public void clear(){
		settings= new xmlEl("settings");
	}

	private class xmlEl {
		public String name;
		public ArrayList<xmlEl> elements;
		public ArrayList<xmlParam> params;

		public xmlEl(String name) {
			this.name = name;
			params = new ArrayList<xmlParam>();
			elements = new ArrayList<xmlEl>();
		}

		public void addParam(String name, String value) {
			params.add(new xmlParam(name, value));
		}

		public String toString() {
			// TODO sistemare indentazione
			String res = "<" + this.name;
			for (xmlParam p : params) {
				res += p;
			}
			if (elements.size() == 0)
				res += " />";
			else {
				res += ">";
				for (xmlEl e : elements) {
					res += "" + e;
				}
				res += "</" + this.name + ">";
			}
			return res;
		}

	}

	private class xmlParam {
		public String name;
		public String value;

		public xmlParam(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String toString() {
			return " " + name + "=\"" + value + "\"";
		}
	}
}
