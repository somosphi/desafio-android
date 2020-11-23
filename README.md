# Desafio Android - Phi
---
![Banner Phi](assets/20200826084504887_picture.jpg)

## Olá, ficamos felizes por você participar do nosso desafio Android!

---

## Como será seu desafio

Utilizando a nossa [API](https://desafio-ios-phi-bff.herokuapp.com/), você deverá criar uma app que apresente o saldo e as movimentações financeiras de um usuário, uma tela com os detalhes da movimentação selecionada e a opção de compartilhamento do mesmo. O layout deverá ser semelhante ao apresentado neste `README`. Legal, certo?

Pense no desafio como uma oportunidade de mostrar todo o seu conhecimento. Faça-o com calma! Você pode combinar o tempo com nosso time de RH!
Vamos avaliar como você lida com as seguintes situações:

- Consumo de APIs;
- Construção de Layout a partir de um modelo de tela;
- Persistência de dados (Ocultar saldo);
- Estruturação de layout;
- Lógicas de apresentação de variações de layout;
- Fluxo da aplicação.

Os pré-requisitos são: 

1. Código-fonte deve ser escrito em inglês, pois trata-se do idioma padrão para escrita de qualquer software.
2. O build sobre a solução deve ter sucesso quando o candidato der o OK que o teste pode ser avaliado pela Phi.

Fora isso, sinta-se a vontade para:

- Usar ou não usar bibliotecas;
- Adotar a arquitetura que você quiser.

Nos preocupamos com qualidade e acreditamos bastante em testes automatizados. Entretanto, sabemos que não é um tópico dominado por todos, por isso aceitamos desafios com todos os perfis e diferentes momentos de experiência e conhecimento técnico.

Para posições mais Seniors, porém, damos muita importância para a qualidade do código.

---

## Features

* Tratamento de erros e apresentação de: Error generico, loading;
* Buscar saldo da rota `myBalance`;
* Ao tocar no ícone de olho do saldo, o mesmo deve ser escondido e apresentado conforme modelo de tela anexado. OBS: Esta flag deverá ser persistida.
* Buscar lista de itens do extrato da rota `myStatement` com paginação de 10 itens por request(scroll infinito);
* Ao tocar em um item do extrato deverá abrir uma tela de detalhes.
* A tela de detalhes deverá consumir a rota de `myStatement/detail` informando o `id` do item selecionado como parâmetro;
* Ao tocar no botão de compartilhar, deverá ser compartilhada a imagem da área de informações do comprovante abrindo o componente de compartilhamento (sharing) padrão do Android.

---

## Api

[Documentação](https://desafio-ios-phi-bff.herokuapp.com/)

Seu token deve ser enviado no header de todas as requisições como header param `token`

Token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c`

---

## Layout

![ColorPallet](assets/CollorPallet.png)


<img src="assets/iPhone X, XS, 11 Pro – 1.png"
alt="" width="375" height="812" border="1" /> | <img src="assets/iPhone X, XS, 11 Pro – 2.png"
alt="" width="375" height="812" border="1" />

<img src="assets/iPhone X, XS, 11 Pro – 3.png"
alt="" width="375" height="812" border="1" /> | <img src="assets/iPhone X, XS, 11 Pro – 4.png"
alt="Compartilhamento" width="375" height="812" border="1" />

### Imagem a ser compartilhada:

<img src="assets/Compartilhar.png"
alt="Comprovante" width="375" height="562" border="1" />

---

## Processo de submissão



:exclamation: **ATENÇÃO** :exclamation:

Não tente fazer o PUSH diretamente para ESTE repositório!

## Copyright

Copyright ícones: [zondicons](https://www.iconfinder.com/iconsets/zondicons)

## Agradecimento

Obrigado ao @micheltlutz por ser o idealizador do teste técnico Android/iOS da @somosphi.