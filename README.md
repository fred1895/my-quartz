# QUARTZ SCHEDULE COM MULTIPLOS JOBS
Repositório em Spring  usando QUARTZ SCHEDULE agendando e customizando Jobs.

# Menu
* [Como rodar?](#start-my-quartz)
* [Todos os Jobs](#todos-os-jobs)
* [Jobs especificos](#jobs-especificos)
* [Erros de validacao](#erros-de-validacao)

# Como rodar?
**ATENÇÃO: O PROJETO ESTÁ RODANDO NA PORTA 8090**

```
#CLONE
git clone https://github.com/fred1895/my-quartz.git

# START-MY-QUARTZ
cd my-quartz
mvn install
mvn spring-boot:run

Ou rode diretamente na IDE
Precisa mudar a variavel de ambiente para local

```

[Voltar ao menu](#menu)

# Todos os Jobs
Nesse endpoint terão serviços para inicar, pausar e reiniciar todos os jobs. <br> 
ATENÇÃO: Use apenas esse Endpoint se você não tiver iniciado nenhum Job ainda.

### Base URL
{baseUrl}: `http://localhost:8090/all-jobs`  </br>
[Clique aqui para ver a camada de controller](https://github.com/fred1895/my-quartz/blob/master/src/main/java/br/com/wod/quartz/resource/AllJobsResource.java) 
</br>

`GET {baseUrl}/start`</br>
Inicia todos os jobs. Se não for feita nenhuma configuração de tempo antes, inicia com o agendamento default de execução a cada 10 segundos infinitamente.</br>
Status 200 
</br>
</br>

`GET {baseUrl}/pause`</br>
Pausa todos os jobs que foram iniciados</br>
Status 200 
</br>
</br>


`GET {baseUrl}/resume`</br>
Reinicia todos os jobs pausados</br>
Status 200 
</br>
</br>

[Voltar ao menu](#menu)

# Jobs especificos

## Endpoints
O projeto possui 4 jobs de 2 empresas diferentes. Elas estão padronizadas assim: </br>
### Base URL
`http://localhost:8090/{nomeDaEmpresa}/job/{numJob}`  </br>

*__{nomeDaEmpresa}__*: O nome da empresa, exemplo: enelsp, cpfl...
</br>
*__{numJob}__*: O nome que irá identificar qual job respectivo para a empresa passada na Query. Exemplo: first, second...
</br>
</br>

## Exemplo
Usarei o endpoint da enel pra exemplificar. [Clique aqui para ver a camada de controller](https://github.com/fred1895/my-quartz/blob/master/src/main/java/br/com/wod/quartz/resource/EnelSpFirstJobResource.java)  </br>

{baseUrl}: `http://localhost:8090/enelsp/job/first`

</br>

`GET {baseUrl}/start`</br>
Inicia o job selecionado. Se não for feita nenhuma configuração de tempo antes, inicia com o agendamento default de execução a cada 10 segundos infinitamente.</br>
Status 200 
</br>
</br>

`GET {baseUrl}/pause`</br>
Pausa o Job selecionado</br>
Status 200 
</br>
</br>

`GET {baseUrl}/resume`</br>
Reinicia o Job selecionado caso o tenha pausado</br>
Status 200 
</br>
</br>

`GET {baseUrl}/delete`</br>
Deleta o Job selecionado. MUITO CUIDADO!</br>
Status 200 
</br>
</br>

`GET {baseUrl}/info`</br>
Retorna informacoes do Job selecionado. WARNING: Caso nao tenha iniciado, retorna 400 com mensagem dizendo para iniciar job. Exemplos abaixo:</br>
Caso seja uma config de hora, minuto ou segundo retornará com essa resposta:

```
{
    "job-name": "Enel SP first job",
    "job-group": "EnelSp",
    "job-description": "Primeiro exemplo de job vindo da Enel Sao Paulo rodando",
    "previous-fire-time": 2020-11-12@15:43:00,
    "next-fire-time": "2020-11-12@15:53:00"
    "repeat-interval": 10000
    "times-triggered": 15
}
```

Status 200 <br>

*__{previous-fire-time}__*: O ultimo instante em que o job foi executado. Padrão da data: "yyyy-MM-dd@HH:mm:ss".
</br>
*__{next-fire-time}__*: O proximo instante em que o job será executado. Padrão da data: "yyyy-MM-dd@HH:mm:ss".
</br>
*__{repeat-interval}__*: Os milisegundos entre as execuções do job.
</br>
*__{times-triggered}__*: O numero de vezes que o job foi executado desde a sua inicialização.

<hr>

Caso seja uma daily config (ex: a configuracao onde envia hora e minuto, e ele é executado diariamente no horario informado), os atributos "repeat-interval" e "times-triggered" não serão retornados.

```
{
    "job-name": "Enel SP first job",
    "job-group": "EnelSp",
    "job-description": "Primeiro exemplo de job vindo da Enel Sao Paulo rodando",
    "previous-fire-time": "2020-11-12@15:53:00",
    "next-fire-time": "2020-11-13@15:53:00"
}

```

Status 200

<hr>

Caso tente pegar as informações antes de inicar o job, será retornado esse JSON:

```
{
    "status-code": 400,
    "http-status": "BAD_REQUEST",
    "message": "Start the job to get the information about it",
    "timestamp": 1605293886816
}
```
Status 400
<br>

### Endpoints de configuração
Para esses endpoints sera necessario passar um Objeto no corpo da requisição. Segue abaixo os exemplos <br>

`POST {baseUrl}/config/dia`</br>
São passados hora e minuto para o Job ser executado uma unica vez por dia no horario escolhido. Padrão 24h.
```
{
    "hour": 10,
    "minute": 30
}
```
O Job será executado todo dia às 10:30.
</br>
Status 201
</br>
</br>

`POST {baseUrl}/config/hora`</br>
É passado a hora para o Job ser executado com o intervalo de tempo informado. Padrão 24h.
```
{
    "hour": 2
}
```
O Job será executado todo dia de 2h em 2h.
</br>
Status 201
</br>
</br>


`POST {baseUrl}/config/minuto`</br>
É passado os minutos para o Job ser executado com o intervalo de tempo informado.
```
{
    "minute": 30
}
```
O Job será executado todo dia de 30min em 30min.
</br>
Status 201
</br>
</br>

`POST {baseUrl}/config/segundo`</br>
É passado os segundos para o Job ser executado com o intervalo de tempo informado.
```
{
    "second": 40
}
```
O Job será executado todo dia de 40s em 40s.
</br>
Status 201
</br>
</br>

[Voltar ao menu](#menu)

# Erros de validacao

### Exemplo

```
{
    "status-code": 400,
    "http-status": "BAD_REQUEST",
    "messages": [
        "The hour should not be greater than 23",
        "The minute should not be greater than 59"
    ],
    "time-stamp": 1605290348489
}
```

Status 400

