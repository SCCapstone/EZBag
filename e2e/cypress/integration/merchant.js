describe('Merchant Scanner Tester', () => {
  it('Adds and removes products', () => {
    // login to debug store
    cy.visit('http://localhost:9000/#/login')
    cy.get('#email').type('owner@store.com')
    cy.get('#password').type('password{enter}')

    // navigate to merchant scanner
    cy.get('#hamburger').click()
    cy.get('#merchant-scanner-link').click()

    // add test product to backend
    cy.get('#merchant-scanner').then((scanner_vue) => {
      scanner_vue.get(0).__vue__.findAndLoadProduct('1234')
    })
    cy.get('#name').type('Test Product')
    cy.get('#description').type('A test product made by cypress')
    cy.get('#price').type('9.99')
    cy.get('#tax').type('3')
    cy.get('#price').click()
    cy.get('#add-product').click()

    // navigate to customer scanner
    // cy.get('#hamburger').click()
    // cy.get('#customer-scanner-link').click()

    // scan test product
    // cy.get('#customer-scanner').then((scanner_vue) => {
    //   scanner_vue.get(0).__vue__.findAndLoadProduct('1234')
    // })
    // cy.get('#add-product').click()

    // navigate to login, which should redirect to merchant page
    cy.visit('http://localhost:9000/#/login')

    // navigate to merchant scanner
    cy.get('#hamburger').click()
    cy.get('#merchant-scanner-link').click()

    // remove test product from backend
    cy.get('#merchant-scanner').then((scanner_vue) => {
      scanner_vue.get(0).__vue__.findAndLoadProduct('1234')
    })
    cy.get('#delete-product').click()
    
  })
})