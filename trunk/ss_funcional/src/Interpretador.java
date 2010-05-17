import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Interpretador {

	private BufferedReader br = null;
	private Map<String, Funcao> funcoes = new HashMap<String, Funcao>();
	private String token;
	private String[] tokens;
	private Integer pToken = 0;

	public static void main(String[] args) throws Exception {

		Interpretador interpretador = new Interpretador();
		interpretador.leArquivo("prog01.func");
		interpretador.parse();

		System.out.println("OK!");

	}

	private void leArquivo(String filename) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filename));
	}

	private void parse() throws IOException {
		String linha = br.readLine();

		if (linha.equals(":defs")) {
			leDefs();
		}
		
		linha = br.readLine();
		
		if (linha.equals(":run")) {
			leRun();
		}
	}

	private void leRun() throws IOException {
		String run = br.readLine();
		System.out.println(run);
	}

	private void leDefs() throws IOException {
		String def = br.readLine() + " $";

		while (def != null && !def.equals(":run") && !def.equals(" $")) {
			String[] defs = def.split(" +");
			leDef(defs);
			def = br.readLine() + " $";
		}
	}

	private void leDef(String[] defs) {
		tokens = defs;
		nextToken();
		Funcao funcao = new Funcao();
		if (token.matches("\\D\\p{Alpha}*")) {
			funcao.setNome(token);
			nextToken();
			if (token.equals("=")) {
				nextToken();
				while (!token.equals(".") && !token.equals("$")) {
					funcao.addArgumento(token);
					nextToken();
				}
				// verificação de segurança
				if (token.equals("$")) {
					System.out.println("ERRO de parser!");
					System.exit(0);
				}

				nextToken();
				while (!token.equals("$")) {
					funcao.addFragCorpo(token);
					nextToken();
				}
			}
		} else {
			System.out.println("ERRO de parser: ");
			System.exit(0);
		}
		funcoes.put(funcao.getNome(), funcao);
	}

	private Boolean nextToken() {
		try {
			token = tokens[pToken];
			pToken++;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

}
