// https://docs.cypress.io/api/introduction/api.html

describe('Navigation Test', () => {
  it('Opens the cart view', () => {
    cy.visit('/')
    cy.contains('$').click()
    cy.url().should('include', '/#/cart')
  })
})
