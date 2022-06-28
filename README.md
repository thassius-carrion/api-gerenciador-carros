

**API de Gerenciamento de Carros por Usuário – Orange Talents**

**Criação do Projeto Maven:**

A primeira coisa a se fazer, é a criação do projeto Maven para que possamos iniciar a nossa API.

O Maven é uma ferramenta de gerenciamento de projetos Java, utilizaremos o Maven para

gerenciar nossas dependências e builds.

Pelo SpringToolSuite4, utilizamos a opção Spring Starter Project. Por ela é possível escolher

algumas dependências que iremos utilizar durante o projeto:

*Figura 1 - Iniciando projeto Spring pelo sts4*

Iremos adicionar as dependências **Spring Web** e **Spring Web Services** pois são por meio dessas

dependências básicas que é possível implementar os artifícios que o Springboot nos

proporciona. Como iremos usar JPA Hibernate para a correlação da aplicação com o banco de

dados relacional, também implementamos sua dependência: **Spring Data JPA.**

Deste modo criamos o nosso projeto Maven contendo as dependências e as configurações do

SpringBoot.

Ao decorrer do projeto, podemos implementar outras dependências quando necessárias pelo

arquivo **pom.xml.**

**Configurando o Banco de Dados:**

Para esta aplicação, iremos utilizar o banco de dados H2 localmente. Primeiro passo é adicionar

a dependência do H2 no nosso arquivo **pom.xml:**

*Figura 2 - Dependência BD H2*

Segundo passo é configurar o arquivo **application.properties.** Utilizaremos as configurações

padrões de teste, tanto pro JPA quanto pra conexão com o banco de dados:





*Figura 3 - Arquivo application.properties*

**Modelagem:**

Antes de colocarmos a mão na massa, precisamos pensar em como iremos implementar nossa

API REST. O objetivo da API é **gerenciar veículos de usuários**, assim, teremos duas entidades,

Usuário e Carro, onde um usuário poderá ter vários carros, mas cada carro só poderá estar

relacionado há um usuário, como no Diagrama de Classes abaixo:

*Figura 4 - Diagrama de Classes da API*

Para a arquitetura da API, seguiremos o padrão MVC. Nosso objetivo como backend é

desenvolver os *Models* e os *Controllers.*

Os *Controller*s são utilizados para lidar com a ligação da View com as outras partes do sistema

que são a regra de negócio e banco de dados.

Os *Models* são responsáveis pela leitura, escrita de dados e de suas validações. Dentro dele

teremos as interfaces de *Repositories*, as classes de *Services* as classes DTO (quando necessário),

onde:

•

•

**Repository**: Design Pattern onde os dados são obtidos do banco de dados.

**DTO (Data Transfer Object):** são objetos apenas com atributos e seus métodos

assessores, sem regra de negócio. Ele é importante para filtrar o tipo de informação que

precisa ser recebida ou enviada pelo usuário.

•

**Service**: seria outro Desing Pattern onde há somente a regra de negócio e não tem

acesso direto ao banco de dados.

Ao desenvolver um sistema você vai escolher quais você vai utilizar. Para o nosso caso, será

utilizado Entity + DTO + Service + Repository + Controller.





*Figura 5 - Modelagem MVC*

**Associação Usuario-Carro:**

Considerando que a API irá gerenciar carros de usuários reais, desta forma, um carro não pode

pertencer à dois usuários diferentes, mesmo sendo do mesmo modelo, marca, ano, etc... Então

a associação entre Usuario-Carro será **Um-Para-Muitos**, onde o id, que será a chave primária,

pode ser considerado como placa do carro, sendo única por Carro.

A associação poderia também ser do tipo Muitos-Para-Muitos caso trabalhássemos com “tipos”

de carro, onde um Carro teria atributos marca, modelo, ano, mas não teria por exemplo a placa

ou número de chassi do carro, assim duas pessoas poderiam ter carros iguais, do mesmo ano,

da mesma marca, do mesmo modelo, sendo necessário instanciar apenas um objeto Carro para

dois Usuários diferentes.

Esta forma seria melhor para a memória do sistema, mas eu achei mais interessante trabalhar

com uma associação **Um-Para-Muitos**, pois a API pode ser expandida no futuro e ter atributos

próprios individuais do usuário nos seus carros, por exemplo, se um usuário quiser vender seu

carro em um valor diferente da tabela FIPE, ele poderia mudar o valor do seu Carro, sem

interferir no valor do carro de outros usuários.





**Entidade Usuario:**

OBS: Omitindo na imagem Construtor com argumentos, getters e setters, hashCode e equals.

A entidade é a classe onde utilizamos as ferramentas de mapeamento do JPA para modelar

como queremos:

**@Entity** -> Utilizamos esta anotação para informar o JPA que nossa classe é uma entidade. Em

Orientação a Objetos, uma entidade é uma tabela no banco de dados e o JPA automaticamente

estabelece uma ligação entre a entidade e uma tabela.

**@Table(name = "tb\_usuario")** -> Utilizamos esta anotação simplesmente para modificar o

nome da tabela, por padrão o JPA criaria a tabela com o nome da própria entidade. Lembrando

que por padrão, o JPA criará as colunas da tabela com o nome dos atributos da classe.

**@Id e @GeneratedValue(strategy = GenerationType.IDENTITY)** -> Utilizamos esta anotação

para informar o JPA de se responsabilizar por auto-incrementar e gerenciar nossa chave

primária.

**@Column(unique = true, nullable = false)** -> Estamos avisando o JPA que a coluna do atributo

com essa notação deve ser única (unique = true) e não pode ser nula (nullable = false).

**@JsonFormat(pattern = “dd/MM/yyyy”)** -> apenas formatando como a data deve ser inserida

pelo JSON.

**@OneToMany(mappedBy = “usuário”)** -> Fazendo uma associação Um-Para-Muitos pelo JPA.





**Entidade Carro:**

OBS: Omitindo na imagem Construtor com argumentos, getters e setters, hashCode e equals,

métodos das regras do rodízio.

**@JsonIgnore** -> Sem essa notação, entraríamos em um problema de Loop quando fizéssemos o

Get Usuario ou Get Carro, pois o usuário mostra sua lista de carros (que é como queremos

apresentar os dados), mas o Carro também mostraria seu Usuario, que mostraria seus Carros,

que mostraria seu Usuario, etc... Entrando em um problema de loop. Com essa notação no

atributo usuário, faz com que o Json ignore esse atributo na hora de apresentar os dados,

quando o método get é chamado.

**@ManyToOne** -> Faz parte da modelagem de associação entre as entidades.

**@JoinColumn** -> Estamos avisando o JPA para criar uma coluna na tabela “tb\_carro” com o

nome “usuário\_id”, onde terá o ID do usuário que o carro corresponde.

Nossas duas entidades implementam a interface Serializable, fazendo com que nossos objetos

possam ser transformados em cadeia de bytes. Queremos isso na nossa aplicação WEB para que

nossos objetos possam trafegar na rede, possa ser gravado em arquivos, etc.

**Implementando as Regras do Rodízio:**

Iremos criar essas regras dentro da própria entidade Carro, e os atributos serão setados dentro

do construtor, sem precisar passar argumentos para setar eles.





Para ficarmos com o código limpo, criaremos métodos privados dentro da classe carro para

elaborar a lógica dos dois atributos. Depois esses métodos serão chamados dentro do

Construtor para atribuir o valor aos atributos.

**1)** Atributo **diaDeRodizio - String,** baseado no último número do ano do veículo, tal que:

Final 0-1: segunda-feira

Final 2-3: terça-feira

Final 4-5: quarta-feira

Final 6-7: quinta-feira

Final 8-9: sexta-feira

Criamos os métodos:

Onde o lastDigit() recebe um numero inteiro n e retorna o ultimo numero desse inteiro, por

exemplo: se int n = 685754, return “4”;

Depois criamos o método diaRodizio() que recebe um inteiro n, aplica o lastDigit, e a partir disso

utilizamos a técnica switch case que compara o valor desse lastDigit com os valores de cada

caso, e nos retorna uma String, de acordo com a regra do rodizio que nos foi pedida.

Com esses dois métodos o atributo diaDeRodizio já receberá o valor desejado.

**2)** Atributo **statusRodizio - boolean**, que compara a data atual do sistema com as condicionais

anteriores e, quando for o dia ativo do rodizio, retorna *true*; caso contrário, *false*.

Para este atributo, implementamos mais dois métodos:





**pegarDiaDeHoje()** -> Nos retorna uma String do dia da semana de hoje, de acordo com o sistema

local, por exemplo, se rodar este método em uma segunda feira, ele retorna uma String de valor

“**segunda-feira**”, a resposta depende do dia da semana do sistema.

**temRodizio()** -> Compara o resultado do método acima com o atributo diaDeRodizio, se forem

iguais, quer dizer que os dias são os mesmos, e o rodizio deve estar ativo ou seja, return *true*,

caso contrário, *false*.

Os dois atributos, diaDeRodizio e statusRodizio não tem setters, apenas getters, pois não faz

sentido setarmos esses atributos diretamente pelo set, sem que eles passem pelas nossas

regras.

Outra observação é que toda vez que o getStatusRodizio() é chamado, o atributo é atualizado,

isso acontece pois para cada vez que queremos acessar o atributo statusRodizio, ele pode ser

diferente do dia que o objeto foi criado, e como só acessamos esse atributo por meio do get, o

usuário da API nunca receberá a informação errada, pois ele será atualizado antes da informação

chegar ao usuário.

Os métodos também são chamados no construtor:

**Repositories:**

Como o JPARepository é uma interface, nossos repositories também serão interfaces. A

JpaRepository é uma extensão específica do repositório da JPA e estenderá nosso repository

deste modo, já ganhamos a implementação de vários métodos como findAll, findOne, save,

exists, etc...





**Controllers:**

O *Controller* é a classe responsável por mapear e direcionar o fluxo na nossa aplicação,

“conversando” com a camada de serviço após receber algum request do usuário. É nesta camada

que definimos as URLs da aplicação e o que cada chamada da URL irá responder por meio dos

métodos HTML Get, Post, Delete, Put.

É a partir desta camada *Controller* que o front-end começa a trabalhar nas Views, pois são

exatamente essas chamadas de URLs que eles utilizam para fazer o front-end.

**UsuarioController:**

A

anotação *@RestController* garante que minha classe seja um controlador REST

e *@ResquestMapping* permite que eu customize a rota de acesso pela URI.

A partir disso injeto um UsuarioService, e criei dois métodos GET e um método POST.

O método GET padrão irá retornar a lista de todos os usuários já cadastrados. Já o método

GET(value = /{id}) retornará o usuário por ID.

O método POST faz um cadastro de um novo usuário, ttilizamos a notação @RequestBody para

avisar que o objeto chegará pelo em JSON, e esse JSON deve ser deserializado para meu objeto

Usuario.





**CarroController:**

No CarroController também foram criados dois métodos GET e um método POST para cadastro.

Para o carro criamos um CarroRequestDTO, para filtrar o que deve ser passado por meio de JSON

para o cadastro de um novo Carro. Isso foi necessário para relacionar um usuário com um carro

na hora do cadastro.

A ideia é que sempre que um Carro é criado, ele já deve ser relacionado ao seu usuário, deste

modo fizemos um POST de modo que o usuário da API deve passar um usuarioId pelo JSON além

das informações básicas do carro.

OBS: Omitindo Getters.





**Services:**

A classe de serviço serve para criar métodos públicos com as regras de negócio da API. Como já

visto acima, nossos *Controllers* não tem acesso diretamente ao nosso *Repository*, quem tem esse

poder de acessar o *Repository* é a camada de serviço.

Em outras palavras, a aplicação WEB não tenha acesso diretamente ao Banco de Dados, criando

uma camada de segurança na nossa aplicação.

**UsuarioService:**

@Service avisamos nossa aplicação que essa classe é uma classe de serviço.

Para a injeção do repository, aplicamos o recurso de injeção de dependência do Spring,

@Autowired. O resto da classe é bem padrão de uma classe de serviço, que não tem regras de

negócio especificas.





**CarroService:**

Durante o desenvolvimento, primeiro criamos um CarroService padrão (neste momento ainda

não tínhamos nem criado o CarroRequestDTO). Nosso insert ainda estava recebendo um Carro.





**Tratamento das exceções:**

1- Criamos a classe ResourceNotFoundException dentro do pacote services.exceptions

Essa será a exceção que será chamada sempre que tivermos um erro durante um GET,

ou seja, durante uma busca.

2- Criamos a classe BadRequestdException dentro do pacote services.exceptions

Essta será a exceção que será chamada sempre que tivermos um problema durante o

POST, ou seja, durante um cadastro.

3- Sempre que fazemos uma requisição com erro, por padrão o Spring nos retorna um

objeto de erro do tipo abaixo:

Como queremos fazer o tratamento manual das exceções e retornar um objeto parecido com o

acima, iremos criar a classe StandardError com os atributos iguais estes, obs: dentro do pacote

*Controller* pois quem trabalha com requisições são os *controllers*.





4- Criamos a classe ResourceExceptionHandler, está classe que será responsável pelo

tratamento manual das nossas exceções.

**@ControllerAdvise ->** Esta anotação faz com que as exceptions sejam interceptadas para que

nossa classe possa executar o possível tratamento.

**@ExceptionHandler(ResourceNotFoundException.class)** -> meu método ira interceptar as

exceções do tipo ResourceNotFoundException e fará o tratamento de acordo, retornando **o erro**

**tratado** e o status **404 NOT FOUND.**





**@ExceptionHandler(ResourceNotFoundException.class)** -> meu método ira interceptar as

exceções do tipo BadRequestException e fará o tratamento de acordo, retornando o erro

tratado e o status **400 BAD REQUEST.**

As exceções são tratadas nas classes de serviço, de acordo com o método que está sendo

chamado, caso haja um erro, lançar a exceção.

A partir disso, o projeto já estava funcionando, tínhamos a interação com o banco de dados a

partir dos nossos *repositories,* tínhamos uma camada de serviço que utilizava este *repository e*

fazia o básico, buscava informações e inseria informações. Também já tínhamos os nossos

*Controllers* sendo uma aplicação Web e podendo ser testado no POSTMAN, no H2 pelo Browser

e tudo mais.

**Funcionamento do CarroRequestDTO:**

Como foi falado acima, tivemos que fazer uma modificação no CarroService, mais

especificamente no método insert. Este método a partir de agora recebe um CarroRequestDTO

ao invés de um Carro, para que possamos passar um usuarioID por ele.

Foi preciso criar um método toCarro() que transforma um CarroRequestDTO em um objeto

carro, para que possa ser salvo utilizando método save() do JPA, e realmente salvar no banco de

dados.

Observe que é nesse método toCarro() que fazemos a relação do carro-usuário, utilizando o

findById do UsuarioService, sim, tivemos que injetar um UsuarioService dentro de nosso

CarroService.





**Spring-Cloud-Feign e o valor FIPE:**

Nosso programa já está funcionando e integrado com BD e tudo mais, mas ele ainda não está

consumindo a API da FIPE para obter os dados do valor do veículo baseado na marca, modelo,

etc.. Mas para completarmos o desafio, precisamos disso certo? Certo!!! Então vamos lá:

Uma forma de consumir outras APIs/Web Services internamente, é através de uma peça que

execute solicitações HTTP de modo sincrono. Para isso temos diversos, o mais conhecido é o

RestTemplate, já do Spring. No entanto aqui iremos utilizar uma lib chamada de Feign, já

presente no Spring Cloud. O Feign foi construído para facilitar o consumo de diversos serviços.

Para utilizar, primeiro precisamos adicionar sua dependência no pom.xml:

Em nossa classe main, devemos adicionar a anotação destacada abaixo:

Para chegarmos no valor Fipe, precisamos fazer uma interação por cascata, pois um request

depende do resultado do anterior, e assim por diante.

Request 1 - [GET:](https://parallelum.com.br/fipe/api/v1/carros/marcas)[ ](https://parallelum.com.br/fipe/api/v1/carros/marcas)<https://parallelum.com.br/fipe/api/v1/carros/marcas>

Resposta 1 –





Precisamos criar um DTO que será a representação do objeto dessa primeira resposta, ou seja,

uma classe com nome e código (os dois String), chamaremos de MarcaDTO:

Com isso, o Feign Client nos diz que precisamos criar uma interface, que por sua vez será

responsável por realizar as requisições ao WebService. Foi criada como:

Onde a url é o caminho da primeira Request, foi adicionado no nosso application.properties.

E na interface criamos o método que retorna a resposta que vimos acima, ou seja, uma list de

MarcaDTO.

Agora, basta utilizar a interface acima em na nossa classe CarroService. Neste caso, criamos um

método que irá retornar o código da marca:

Obs: queremos que nos retorne o código e não o nome da marca, pois para o Request 2 é

necessário o código.





Para testar, debugamos o método acima, e o resultado é incrível, o Feign salva dentro da nossa

List de MarcaDTO todas as informações que ele recebe da outra API:

Beleza, funcionou! E agora? Temos que ir para o nosso segundo request:

Request 2 - [GET:](https://parallelum.com.br/fipe/api/v1/carros/marcas/1/modelos)[ ](https://parallelum.com.br/fipe/api/v1/carros/marcas/1/modelos)<https://parallelum.com.br/fipe/api/v1/carros/marcas/1/modelos>

Resposta 2 –

Para modelar essa segunda resposta, tivemos que criar mais 3 DTOs, ModeloDTO, ModeloDTO1,

MOdeloDTO2 onde a ModeloDTO faz a integração das outras duas. Isso foi necessário pois essa

segunda resposta, nos retorna duas listas de diferentes tipos String,Integer e String,String.





Dessa forma, minha classe ModeloDTO consegue modelar exatamente o que o request 2 está

retornando.

Assim podemos criar em nossa interface, o método que retornará esse request:

Onde {código} será o código da marca, que será passado por parâmetro do método. Na chamada

desse método podemos chamar o outro método que criamos no request 1, pegar o código da

marca, e passar como parâmetro para o request 2.

E assim criamos o método getCodigoModelo, para pegar o código do modelo, pois iremos

precisar para o request 3. Observe que um sempre vai dependendo do outro.





Debugando o código para analisar se estamos conseguindo pagar os valores de Modelos

(Nome, Codigo) e Anos(Nome, Codigo), podemos ver que o Feign funciona perfeitamente.

E deste mesmo modo fizemos para os:-

Request 3 –

GET: - <https://parallelum.com.br/fipe/api/v1/carros/marcas/59/modelos/5940/anos>

Request 4 –

GET:

<https://parallelum.com.br/fipe/api/v1/carros/marcas/59/modelos/5940/anos/2014-3>

**Métodos na interface FipeClient:**

Veja que no último método, já dependemos de 3 códigos para serem passados como

parâmetros.

**Metodos no CarroService:**

E por final, para que nosso metodo insert, ficasse mais legível, criei um metodo getValorFinal()

que integra todos os métodos do Feign em um metodo só, sendo necessário apenas esse

método ser chamado no nosso insert:





**Metodo getValorFinal():**

**Metodo Insert():**

E desta forma, toda vez que um Carro é criado por meio do POST, nossos métodos do Feign

serão acionados.

Iremos utilizar as informações que serão passadas por meio do CarroRequestDTO, que por sua

vez, são as informações que são passadas em JSON, contendo a marca, o modelo, etc...

Pegaremos essas informações, acionaremos nossos métodos que fazem a interação Feign acima,

faremos a comparação entre nomes de marcas, nomes de modelos, anos, tipo de combustível,

e após essa consulta em cascata, retornará o valor em String do carro!!

OBS: Se no nosso POST, passamos um carro que não exista no banco de dados Fipe, teremos um

erro de BadRequestException.

O código pode ser encontrado no link:

<https://github.com/thassius-carrion/api-gerenciador-carros>

Para evidenciar o funcionamento da API, eu tomei a liberdade de fazer um vídeo, que pode ser

baixado pelo link compartilhado abaixo:

<https://drive.google.com/file/d/1iIrUq8sGqe-3mOvqNBw89LtRSDCTX2vo/view?usp=sharing>

