# QUARTZ SCHEDULE COM MULTIPLOS JOBS
Repositório em Spring  usando QUARTZ SCHEDULE agendando e customizando Jobs.
</br>
## Como rodar?
ATENÇÃO: O PROJETO ESTÁ RODANDO NA PORTA 8090
```
#CLONE
git clone https://github.com/fred1895/my-quartz.git

# START QUARTZ-MANAGER-WEB
cd my-quartz
mvn install
mvn spring-boot:run

Ou rode diretamente na IDE
Precisa mudar a variavel de ambiente para local

```

## Endpoints
O projeto possui 4 jobs de 2 empresas diferentes. Elas estão padronizadas assim: </br>
### Base URL
`http://localhost:8090/{nomeDaEmpresa}/job/{numJob}`  
*__{nomeDaEmpresa}__*: O nome da empresa, exemplo: enelsp, cpfl...
</br>
*__{numJob}__*: O nome que irá identificar qual job respectivo para a empresa passada na Query. Exemplo: first, second...
</br>
</br>

## Exemplo
Usarei o endpoint da enel pra exemplificar </br>
`GET {baseUrl}/start`</br>
Inicia o job selecionado. Se não for feita nenhuma configuração de tempo, inicial com o agendamento default de execução a cada 10 segundos infinitamente.</br>
Status 200 

## PADRÃO MVC
O padrão mais utilizado pelo mercado é o *Model-View-Controller(MVC)*. </br>
Imagine se cada um resolvesse fazer uma aplicação com suas próprias regras? Quem trabalhasse junto ficaria maluco. O MVC veio justamente pra isso. Padronizar o modo
como as aplicações são feitas. Obs: Não confunda com Design Patterns.</br>
Neste padrão a aplicação back-end fica dividida em camadas, como exemplificado na imagem abaixo:
<img align="center" alt="Padrao MVC" src="doc/imgs/padrao_mvc.png?raw=true" />
## Menu
* [Camada de domínio](#camada-de-dominio)
* [Camada de acesso a dados](#camada-de-acesso-a-dados)
* [Camada de serviço](#camada-de-servico)
* [Controladores REST](#controladores-rest)

### Camada de dominio 
Camada onde estarão as entidades e outras classes responsáveis pela implementação do negócio. 

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque faucibus laoreet nisl at finibus. Vivamus convallis aliquet diam et eleifend. Fusce ut euismod magna, sed dapibus nunc. Fusce iaculis, nulla eget ullamcorper posuere, nisl ligula malesuada erat, at lacinia mauris massa ut neque. Nulla vitae risus at urna volutpat finibus ut nec magna. Nulla sit amet lorem porttitor, mollis libero eu, porttitor turpis. Nullam sit amet nisi eu velit egestas volutpat ac ac sem. Aliquam egestas, justo a lacinia tempus, felis turpis venenatis nunc, a varius velit mauris id ante. Suspendisse nisl magna.

### Camada de acesso a dados 
Camada onde estará a classe(interface) responsavel por fazer a conexão direta com o banco de dados 

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque faucibus laoreet nisl at finibus. Vivamus convallis aliquet diam et eleifend. Fusce ut euismod magna, sed dapibus nunc. Fusce iaculis, nulla eget ullamcorper posuere, nisl ligula malesuada erat, at lacinia mauris massa ut neque. Nulla vitae risus at urna volutpat finibus ut nec magna. Nulla sit amet lorem porttitor, mollis libero eu, porttitor turpis. Nullam sit amet nisi eu velit egestas volutpat ac ac sem. Aliquam egestas, justo a lacinia tempus, felis turpis venenatis nunc, a varius velit mauris id ante. Suspendisse nisl magna, vulputate id risus rhoncus, congue varius tellus. Sed pellentesque quam sed nunc tristique commodo. Cras non est risus. Vestibulum nec nulla vitae ipsum interdum pellentesque.

### Camada de servico 
Camada que aplicará todas as regras de negócio 

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque faucibus laoreet nisl at finibus. Vivamus convallis aliquet diam et eleifend. Fusce ut euismod magna, sed dapibus nunc. Fusce iaculis, nulla eget ullamcorper posuere, nisl ligula malesuada erat, at lacinia mauris massa ut neque. Nulla vitae risus at urna volutpat finibus ut nec magna. Nulla sit amet lorem porttitor, mollis libero eu, porttitor turpis. Nullam sit amet nisi eu velit egestas volutpat ac ac sem. Aliquam egestas, justo a lacinia tempus, felis turpis venenatis nunc, a varius velit mauris id ante. Suspendisse nisl magna, vulputate id risus rhoncus, congue varius tellus. Sed pellentesque quam sed nunc tristique commodo. Cras non est risus. Vestibulum nec nulla vitae ipsum interdum pellentesque.

### Controladores REST
Camada que expões os endpoints necessários para a aplicação cliente fazer as requisições HTTP necessárias

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque faucibus laoreet nisl at finibus. Vivamus convallis aliquet diam et eleifend. Fusce ut euismod magna, sed dapibus nunc. Fusce iaculis, nulla eget ullamcorper posuere, nisl ligula malesuada erat, at lacinia mauris massa ut neque. Nulla vitae risus at urna volutpat finibus ut nec magna. Nulla sit amet lorem porttitor, mollis libero eu, porttitor turpis. Nullam sit amet nisi eu velit egestas volutpat ac ac sem. Aliquam egestas, justo a lacinia tempus, felis turpis venenatis nunc, a varius velit mauris id ante. Suspendisse nisl magna, vulputate id risus rhoncus, congue varius tellus. Sed pellentesque quam sed nunc tristique commodo. Cras non est risus. Vestibulum nec nulla vitae ipsum interdum pellentesque.

