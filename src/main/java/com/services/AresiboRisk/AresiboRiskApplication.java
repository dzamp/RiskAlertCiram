package com.services.AresiboRisk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import org.apache.jena.util.FileManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.List;

import com.services.AresiboClasses.*;


@SpringBootApplication
@RestController
@CrossOrigin
public class AresiboRiskApplication {

	private static final Logger logger = LogManager.getLogger(AresiboRiskApplication.class);
	private static int counter = 20000;
	private static OntModel model;

	public static void main(String[] args) {
		model = ModelFactory.createOntologyModel();
		FileManager.get().readModel(model, "http://users.uoa.gr/~sdi1500080/rsb/CIRAM.owl");
//		model = RDFDataMgr.loadModel("http://users.uoa.gr/~sdi1500080/rsb/CIRAM.owl");
		SpringApplication.run(AresiboRiskApplication.class, args);
	}

	@RequestMapping(value="/getLevels")
	@ResponseBody
	public ResponseEntity<List<Levels>> getLevels(@RequestParam List<String> risks, @RequestParam List<Float> distance, @RequestParam int vehicle_id, 
													@RequestParam double lon, @RequestParam double lat, @RequestParam long timestamp) throws Exception {
		System.out.println("Get levels of risk");
		List<Levels> allLevels = new ArrayList<Levels>();
		int counter = 0;
		for (float dist : distance) {
			Levels risk = new Levels();
			risk.setName(risks.get(counter));
			risk.setImpact("medium");
			if (dist < 20)
				risk.setVulnerability("low");
			else if (dist < 40)
				risk.setVulnerability("medium");
			else
				risk.setVulnerability("high");

			if (risk.getVulnerability() == "high" || risk.getVulnerability() == "medium")
				risk.setThreat("high");
			else
				risk.setThreat("low");

			String t = risk.getThreat();
			String v = risk.getVulnerability();

			if (t == "low" && v == "low")
				risk.setRisk("low");
			else if (t == "low" && (v == "medium" || v == "high"))
				risk.setRisk("medium");
			else if (t == "high" && v == "low")
				risk.setRisk("medium");
			else if (t == "high" && (v == "medium" || v == "high"))
				risk.setRisk("high");

			allLevels.add(risk);
			addRisk(risk);

			// build class for message
			Alert riskAlert = new Alert();
			riskAlert.setAlert_id(this.counter);
			riskAlert.setAlert_level(risk.getRisk().toUpperCase());
			riskAlert.setTimestamp(timestamp); // twrinh wra current time millis
			riskAlert.setAlert_end_time(timestamp);
			riskAlert.setAlert_start_time(timestamp); //not timestamp
			riskAlert.setTracked_entity_id(vehicle_id);
			riskAlert.setLocation(lon, lat);
			riskAlert.setCiram_details(risk);

			ObjectMapper objectMapper = new ObjectMapper();
			//configure objectMapper for pretty input
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			//write object to system.out
			objectMapper.writeValue(System.out, riskAlert);
//			this.producer.sendMessage(riskAlert.toString());
			counter++;
		}

		return new ResponseEntity<List<Levels>>(allLevels, HttpStatus.OK);
	}

	private void addRisk(Levels newRisk) {
		System.out.println("Add risk to ontology model");

		String prefix = "http://www.semanticweb.org/vpap/ontologies/2018/5/untitled-ontology-10#";

		OntClass RiskClass = model.getOntClass(prefix + "Risk");
		OntResource risk = model.createIndividual(prefix + newRisk.getName(), RiskClass);


		// Add impact to risk
		OntClass ImpactClass = model.getOntClass(prefix + "Impact");
		OntResource Impactlevel = model.getIndividual(prefix + newRisk.getImpact());
		Property imp = model.getProperty(prefix, "hasImpact");
		Property impLevel = model.getProperty(prefix, "hasImpactLevel");
		OntResource impact = model.createIndividual(prefix + newRisk.getName() + "_impact", ImpactClass);
		impact.addProperty(impLevel, Impactlevel);
		risk.addProperty(imp, impact);

		// Add threat to risk
		OntClass ThreatClass = model.getOntClass(prefix + "Threat");
		OntResource Threatlevel = model.getIndividual(prefix + newRisk.getThreat());
		Property thr = model.getProperty(prefix, "hasThreat");
		Property thrLevel = model.getProperty(prefix, "hasThreatLevel");
		OntResource threat = model.createIndividual(prefix + newRisk.getName() + "_threat", ThreatClass);
		threat.addProperty(thrLevel, Threatlevel);
		risk.addProperty(thr, threat);

		// Add vulnerability to risk
		OntClass VulnerabilityClass = model.getOntClass(prefix + "Vulnerability");
		OntResource Vulnerabilitylevel = model.getIndividual(prefix + newRisk.getVulnerability());
		Property vuln = model.getProperty(prefix, "hasVulnerability");
		Property vulnLevel = model.getProperty(prefix, "hasVulnerabilityLevel");
		OntResource vulnerability = model.createIndividual(prefix + newRisk.getName() + "_vulner", VulnerabilityClass);
		vulnerability.addProperty(vulnLevel, Vulnerabilitylevel);
		risk.addProperty(vuln, vulnerability);

//		model.write(System.out, "RDF/XML-ABBREV");

	}
}
