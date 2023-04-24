Dados:
	21703860 Pedro Nogueira
	21804816 Ricardo Cunha
	
Nomes dos Filmes:
	The Shawshank Redemption
	The Godfather
	he Dark Knight
	Pulp Fiction
	The Silence of the Lambs
	Inception
	Interstellar
	The Matrix
	The Prestige
	Blade Runner
	
  
Itens a verde são os existentes na app

![image](https://user-images.githubusercontent.com/43844932/234086064-a02aa01b-5d10-4b9d-8213-6dd0546f360d.png)


Previsão da nota 16

class Filme(
    var nomeFilme: String,
    var nomeCinema: String,
    var avaliacao: String,
    var dataVisualizacao: String,
    var observacoes: String,
    var novasFotografias: List<String>,
    var imagemCartaz: Int,
    var genero: String,
    var sinopse: String,
    var dataLancamento: String,
    var avaliacaoImdb: String,
    var linkImdb: String,
    var avaliado: Boolean,
    val uuid: String = UUID.randomUUID().toString(),
    var paraVer: Boolean = false

) {
    fun substituirFotografias- para substituir as fotografias que são tiradas pelo utilizador 
}

registarFilme -valida campos e ve se existe na lista total
filmesVistos- ver se o filme está nos vistos
verificarNomeFilme
percorrerFilmes
verificarNomeCinema
adicionarListaVistos
editOperation
getOperationById
updateData
setCalendario
limparCampos  
alterarAvaliacao
verificarData
verificarNomeCinema

	
Chat gpt, stack overflow e youtube foram usados para fazer varios pedaços de codigo, o botão de pesquisa por voz, ter duas images views horizontal numa lista, criar uma bottom navigation, if para fazer desaparecer partes do xml.
	
