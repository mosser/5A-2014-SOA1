package fr.unice.polytech.soa1.payment.business;


import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton(name = "Payment-DB-Mock")
public class DataAccessObject {

	private List<Retailer> contents;

	public List<Retailer> getContents() { return contents; }
	public void setContents(List<Retailer> retailers) { this.contents = retailers; }

	public DataAccessObject() { init(); }


	public Optional<Retailer> findRetailerById(String id) {
		return getContents().stream().filter(r -> r.getId().equals(id)).findFirst();
	}


	public String toString() {
		StringBuilder builder = new StringBuilder("Database: \n");
		for(Retailer r: contents) {
			builder.append(String.format("[%s] %s - %s :\n", r.getId(),r.getName(), r.getAddress()));
			for(Transaction t: r.getTransactions()) {
				builder.append(t.toString());
				builder.append("\n");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	private void init() {
		// Retailers
		Retailer r1 = new Retailer("s-001", "Auchan", "40, avenue de Flandre - 59170 Croix");
		Retailer r2 = new Retailer("s-002", "Leclerc", "26, quai Marcel-Boyer - 94200 Ivry-sur-Seine");
		Retailer r3 = new Retailer("s-003", "Carrefour", "150 rue Galliéni - 92100 Boulogne-Billancourt");
		// Transactions
		List<Transaction> transactions = new ArrayList<>(Arrays.asList(
				new Transaction("4959189002122739","Bethany Blake",54.0,r3),
				new Transaction("4855018524685878","Ursula Pearson",200.0,r3),
				new Transaction("4662093421537420","Mara Rodgers",58.0,r1),
				new Transaction("4265696396399284","Anne Fletcher",186.0,r1),
				new Transaction("4863169730221859","Judah Henry",83.0,r1),
				new Transaction("4261077848728749","Isabella Hunter",84.0,r2),
				new Transaction("4363472402468320","Winifred Graham",110.0,r2),
				new Transaction("4225900578172872","Dana Rasmussen",136.0,r2),
				new Transaction("4325697017274791","Brady Sexton",44.0,r2),
				new Transaction("4966732984036206","Rafael Chang",97.0,r3),
				new Transaction("4891162685491144","Kylynn Martinez",49.0,r3),
				new Transaction("4585633293446158","Regan Albert",188.0,r3),
				new Transaction("4647295952681453","Rinah Sutton",144.0,r2),
				new Transaction("4784327845368530","Rebecca Beard",81.0,r3),
				new Transaction("4881278930092238","Jonas Acosta",197.0,r1),
				new Transaction("4840035639330743","Emerald Vance",107.0,r3),
				new Transaction("4707008150592447","Benedict Montoya",6.0,r1),
				new Transaction("4715120264189319","Carter Poole",51.0,r1),
				new Transaction("4570679101860154","Xandra Daniel",185.0,r1),
				new Transaction("4243163270363582","Brady Marks",12.0,r3),
				new Transaction("4620760867698119","Keane Valentine",75.0,r1),
				new Transaction("4312179116345940","Hamish Banks",38.0,r2),
				new Transaction("4440481956349686","Lyle Valencia",19.0,r2),
				new Transaction("4869252205779771","Honorato Mendez",23.0,r3),
				new Transaction("4909518084488807","Gannon Davenport",84.0,r2),
				new Transaction("4583689942490302","Ray Blackburn",122.0,r2),
				new Transaction("4379313573334364","Acton Gibson",63.0,r3),
				new Transaction("4481844373652711","Reagan Mercer",115.0,r3),
				new Transaction("4927804057439785","Graiden Baldwin",121.0,r3),
				new Transaction("4724148149741815","Taylor Franks",162.0,r3),
				new Transaction("4120000982843337","Emerald Bartlett",178.0,r3),
				new Transaction("4886993059562511","Christian Dean",170.0,r2),
				new Transaction("4579166674427681","August Shelton",50.0,r1),
				new Transaction("4682094404380762","Ulysses Compton",67.0,r1),
				new Transaction("4152689455961807","Maryam Rhodes",7.0,r3),
				new Transaction("4442336404649541","Nell Glass",192.0,r1),
				new Transaction("4843172253109514","Hamish Solis",24.0,r2),
				new Transaction("4416161858849223","Wesley Molina",133.0,r3),
				new Transaction("4568236983241510","Gavin Graham",107.0,r2),
				new Transaction("4168869270384316","Zena Waters",196.0,r3),
				new Transaction("4382894842885438","Abraham Stafford",65.0,r3),
				new Transaction("4459399668732652","Brenda Everett",112.0,r3),
				new Transaction("4654502564249561","Brennan Lowe",122.0,r1),
				new Transaction("4930190795566880","Barry Todd",60.0,r1),
				new Transaction("4243727514101198","Portia Gutierrez",18.0,r2),
				new Transaction("4535781494341794","Amethyst Burke",178.0,r2),
				new Transaction("4770226434897636","Leah Chapman",64.0,r2),
				new Transaction("4850735664693639","Ivory Carver",150.0,r3),
				new Transaction("4250901758950204","Magee Hinton",146.0,r2),
				new Transaction("4340905536757785","Alan Myers",25.0,r2),
				new Transaction("4993898935057228","Burke Fitzpatrick",116.0,r2),
				new Transaction("4771662626648321","Demetrius Larson",93.0,r1),
				new Transaction("4553084653522810","Audrey Morton",142.0,r3),
				new Transaction("4434380891826006","Elmo Atkins",131.0,r3),
				new Transaction("4640914832428092","Tate Browning",181.0,r2),
				new Transaction("4462602738011628","Sonia Holt",97.0,r3),
				new Transaction("4918070834316317","Justina Barton",112.0,r3),
				new Transaction("4920228405762465","Guinevere Smith",67.0,r3),
				new Transaction("4844447112083434","Hanae Albert",99.0,r3),
				new Transaction("4845874892175190","Regina Hooper",198.0,r2),
				new Transaction("4644376555504279","Rylee Powers",30.0,r1),
				new Transaction("4864448094926771","Phoebe Raymond",173.0,r3),
				new Transaction("4732867951737714","Aiko Ward",65.0,r1),
				new Transaction("4223543230351062","Amethyst Cabrera",16.0,r1),
				new Transaction("4546542499307542","Emerson Hensley",136.0,r2),
				new Transaction("4785557408118620","Farrah Garner",130.0,r1),
				new Transaction("4565879635000607","Aline Martin",55.0,r2),
				new Transaction("4389714752417056","Zia Velasquez",119.0,r3),
				new Transaction("4201719266967843","Kaden Preston",9.0,r2),
				new Transaction("4134116618242116","Brenda Mooney",178.0,r2),
				new Transaction("4458584023220468","Derek Pruitt",48.0,r2),
				new Transaction("4484614109853282","Finn Beach",152.0,r3),
				new Transaction("4493516286974770","Quincy William",1.0,r3),
				new Transaction("4113086587470023","Fletcher Mcdaniel",62.0,r1),
				new Transaction("4414804905420161","Mia Mueller",154.0,r3),
				new Transaction("4637243801075960","Stewart Calhoun",51.0,r1),
				new Transaction("4548868081811817","Eve Shaffer",111.0,r3),
				new Transaction("4185031340317807","Rosalyn Oneil",50.0,r1),
				new Transaction("4487979466188696","Jasper Garcia",132.0,r2),
				new Transaction("4699769840762015","Wallace Phelps",130.0,r3),
				new Transaction("4425936877075586","Jakeem Austin",5.0,r3),
				new Transaction("4481878401245928","Daquan Stewart",33.0,r3),
				new Transaction("4471432467410347","Fuller Estes",189.0,r1),
				new Transaction("4879021530598406","Aiko Floyd",101.0,r1),
				new Transaction("4816259293071928","Camille Wiley",28.0,r3),
				new Transaction("4112347299838435","Kareem Glass",142.0,r2),
				new Transaction("4341624269029123","Justina Rivera",134.0,r1),
				new Transaction("4734330127807337","Kyra Hobbs",157.0,r1),
				new Transaction("4932575705600908","Justin Richardson",10.0,r2),
				new Transaction("4186071381112567","Melinda Livingston",117.0,r3),
				new Transaction("4580205019982538","Marshall Rosales",57.0,r2),
				new Transaction("4576952261524276","Kirestin Mendez",140.0,r2),
				new Transaction("4950519476039335","Jacqueline Valdez",86.0,r2),
				new Transaction("4313072971720249","Fitzgerald Goodwin",78.0,r2),
				new Transaction("4700495491875331","Cyrus Reynolds",60.0,r1),
				new Transaction("4497061975346871","Slade Kirk",89.0,r3),
				new Transaction("4998630379838865","Patricia Ruiz",129.0,r1),
				new Transaction("4266375126875934","Tyler Harrington",166.0,r2),
				new Transaction("4786776727763933","Quyn Rasmussen",27.0,r2),
				new Transaction("4200349646806718","Zena Salinas",172.0,r3)));

		// updating status: card number ending by:
		//   0 				=> PROCESSING
		//   1,2,3,4,5,6 	=> APPROVED
		//   7, 8, 9 		=> DECLINED
		transactions.stream().filter(t -> t.getCardNumber().endsWith("1")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("2")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("3")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("4")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("5")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("6")).forEach(t -> t.setStatus(Status.APPROVED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("7")).forEach(t -> t.setStatus(Status.DECLINED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("8")).forEach(t -> t.setStatus(Status.DECLINED));
		transactions.stream().filter(t -> t.getCardNumber().endsWith("9")).forEach(t -> t.setStatus(Status.DECLINED));

		// creating entities
		this.contents = new ArrayList<>();

		List<Transaction> l1 = new ArrayList<>();
		transactions.stream().filter( t -> t.getRetailer().equals(r1)).forEach(l1::add);
		r1.setTransactions(l1);
		contents.add(r1);

		List<Transaction> l2 = new ArrayList<>();
		transactions.stream().filter( t -> t.getRetailer().equals(r2)).forEach(l2::add);
		r2.setTransactions(l2);
		contents.add(r2);

		List<Transaction> l3 = new ArrayList<>();
		transactions.stream().filter( t -> t.getRetailer().equals(r3)).forEach(l3::add);
		r3.setTransactions(l3);
		contents.add(r3);
	}

}
