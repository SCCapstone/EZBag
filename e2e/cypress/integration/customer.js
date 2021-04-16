describe('Cart Tester', () => {

  it('Adds and removes products from the cart', () => {
    cy.visit('customer/179aa3e0fb88f6e4ec0ef0d0f5588d43f93713e7b7e4a5ddd8a3fdd1c39701fa/scan')
    cy.get('.searchbar').should('be.visible').type('example')
    
  })
})