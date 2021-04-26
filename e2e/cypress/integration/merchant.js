describe('Merchant Scanner Tester', () => {
  it('Adds and removes products', () => {
    cy.visit('http://localhost:9000/#/login')
    cy.get('#email').type('owner@store.com')
    cy.get('#password').type('password{enter}')
    cy.get('#hamburger').click()
  })
})