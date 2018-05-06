package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Demonstration for Stream usage when extracting data from StudentRecord database.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class StudentDemo {
	/**
	 * Program entry point.
	 * @param args array of String arguments
	 * @throws IOException if reading from file failed
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = convert(lines);
		
		System.out.println("Studenti s više od 25 bodova: " + vratiBodovaViseOd25(records));
		
		System.out.println("\nBroj odlikaša: " + vratiBrojOdlikasa(records));
		
		System.out.println("\nLista odlikaša:");
		System.out.println(vratiListuOdlikasa(records));
		
		System.out.println("\nLista odlikaša sortirana po broju ukupnih bodova:");
		System.out.println(vratiSortiranuListuOdlikasa(records));
		
		System.out.println("\nPopis JMBAGova studenata koji nisu položili:");
		System.out.println(vratiPopisNepolozenih(records));
		
		Map<Integer, List<StudentRecord>> poOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("\nLista studenata s ocjenom 5:");
		System.out.println(poOcjenama.get(5));
		System.out.println("\nLista studenata s ocjenom 5:");
		System.out.println(poOcjenama.get(3));
		
		
		Map<Integer, Integer> brojOcjenaPoOcjenama = vratiBrojStudenataPoOcjenama(records);
		System.out.println("\nBroj studenata s ocjenom 5:");
		System.out.println(brojOcjenaPoOcjenama.get(5));
		System.out.println("\nBroj studenata s ocjenom 5:");
		System.out.println(brojOcjenaPoOcjenama.get(3));
		
		
		Map<Boolean, List<StudentRecord>> razvrstanoProlazPad = razvrstajProlazPad(records);
		System.out.println("\nStudenti koji su položili:");
		System.out.println(razvrstanoProlazPad.get(true));
		System.out.println("\nStudenti koji nisu položili:");
		System.out.println(razvrstanoProlazPad.get(false));
	}

	/**
	 * Static method that converts list of student record line Strings to list of StudentRecord objects.
	 * @param lines list of database lines
	 * @return list of StudentRecords
	 * @throws NullPointerException if given argument is null
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		Objects.requireNonNull(lines);
		List<StudentRecord> records = new ArrayList<>();
		
		for(String line : lines) {
			if(!line.isEmpty()) {
				String[] lineParts = line.trim().split("\\t+");
				
				String jmbag = lineParts[0];
				String prezime = lineParts[1];
				String ime = lineParts[2];
				double bodoviMI = Double.parseDouble(lineParts[3]);
				double bodoviZI = Double.parseDouble(lineParts[4]);
				double bodoviLAB = Double.parseDouble(lineParts[5]);
				int ocjena = Integer.parseInt(lineParts[6]);
				
				records.add(new StudentRecord(jmbag, prezime, ime, bodoviMI, bodoviZI, bodoviLAB, ocjena));
			}
		}
		
		return records;
	}
	
	/**
	 * Counts number of students who received more than 25 on MI+ZI+LAB.
	 * 
	 * @param records list of StudentRecords
	 * @return number of students who satisfy given condition
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long count = records.stream()
				.filter(student -> student.getBodoviMI()+student.getBodoviZI()+student.getBodoviLAB() > 25)
				.count();

		return count;
	}
	
	/**
	 * Counts number of students who had 5 as final grade.
	 * 
	 * @param records list of StudentRecords
	 * @return number of students who satisfy given condition
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long count = records.stream()
				.filter(student -> student.getOcjena()==5)
				.count();
		
		return count;
	}
	
	/**
	 * Returns list of students who had 5 as final grade.
	 * 
	 * @param records list of StudentRecords
	 * @return list of StudentRecords who satisfy given condition
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream()
				.filter(student -> student.getOcjena()==5)
				.collect(Collectors.toList());

		return odlikasi;
	}
	
	/**
	 * Returns list of students who had 5 as final grade sorted by total points scored in MI+ZI+LAB.-
	 * 
	 * @param records list of StudentRecords
	 * @return sorted list of StudentRecords who satisfy given condition
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream()
				.filter(student -> student.getOcjena()==5)
				.sorted(Comparator.comparing(student -> student.getBodoviMI()+student.getBodoviZI()+student.getBodoviLAB()))
				.collect(Collectors.toList());
		return odlikasi;
	}
	
	/**
	 * Returns list of JMBAGs of students failed.
	 * 
	 * @param records list of StudentRecords
	 * @return list of JMBAGs of students who failed
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		List<String> nepolozeni = records.stream()
				.filter(student -> student.getOcjena()==1)
				.map(StudentRecord::getJmbag)
				.sorted()
				.collect(Collectors.toList());
		
		return nepolozeni;
	}
	
	/**
	 * Classifies students by final grade and creates map with grades as keys and StudentRecord lists as values.
	 * 
	 * @param records list of StudentRecords
	 * @return map of classified students by grades
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> poOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getOcjena));
		
		return poOcjenama;
	}
	
	/**
	 * Counts number of students with each grade and creates map with grades as keys and number of students with given grades as values.
	 * 
	 * @param records list of StudentRecords
	 * @return map of grades and number of students with those grades
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		Map<Integer, Integer> brojStudenataPoOcjenama = records.stream()
				.collect(Collectors.toMap(StudentRecord::getOcjena, z -> 1 , (z,t) -> z+1 ));
		
		return brojStudenataPoOcjenama;
	}
	
	/**
	 * Classifies students by failure. 
	 * If student failed he is put in map under "false" key,
	 * otherwise he is put under "true" key.
	 * @param records list of StudentRecords
	 * @return map of students classified by failure
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> razvrstanoProlazPad = records.stream()
				.collect(Collectors.partitioningBy(student->student.getOcjena()>1));
		return razvrstanoProlazPad;
	}
	
}
