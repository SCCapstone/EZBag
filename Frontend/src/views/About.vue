<template>
  <v-container fluid>
    <v-row justify="center">
      <v-subheader>About Us</v-subheader>

      <v-expansion-panels popout>
        <v-expansion-panel
          v-for="(message, i) in messages"
          :key="i"
          hide-actions
        >
          <v-expansion-panel-header>
            <v-row
              align="center"
              class="spacer"
              no-gutters
            >
              <v-col
                cols="4"
                sm="2"
                md="1"
              >
                <v-avatar
                  size="36px"
                >
                  <img
                    v-if="message.avatar"
                    alt="Avatar"
                    :src="message.avatar"
                  >
                  <v-icon
                    v-else
                    :color="message.color"
                    v-text="message.icon"
                  ></v-icon>
                </v-avatar>
              </v-col>

              <v-col
                class="hidden-xs-only"
                sm="5"
                md="3"
              >
                <strong v-html="message.name"></strong>
                <span
                  v-if="message.total"
                  class="grey--text"
                >
                  &nbsp;({{ message.total }})
                </span>
              </v-col>

              <v-col
                class="text-no-wrap"
                cols="5"
                sm="3"
              >
                <v-chip
                  v-if="message.new"
                  :color="`${message.color} lighten-4`"
                  class="ml-0 mr-2 black--text"
                  label
                  small
                >
                  {{ message.new }} new
                </v-chip>
                <strong v-html="message.title"></strong>
              </v-col>

              <v-col
                v-if="message.excerpt"
                class="grey--text text-truncate hidden-sm-and-down"
              >
                &mdash;
                {{ message.excerpt }}
              </v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <v-card-text v-text="message.excerpt">
            </v-card-text>
            <v-divider></v-divider>
            <v-card-text>
            <v-icon v-text="message.icon"></v-icon>
            <a> </a>
            <a :href="message.link">{{message.alt}}
            </a>
            </v-card-text>
            
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      messages: [
        {
          avatar: 'https://avatars.githubusercontent.com/u/7173077?v=4',
          name: 'Cody Shearer',
          title: '',
          excerpt: 'Cody is an undergraduate studying computer science and mathematics at the University of South Carolina. He is also a software development intern at Krumware, where he will take on a full-time position after graduation.',
          icon: 'mdi-account-multiple',
          alt: 'Github.io',
          link: 'https://cjshearer.github.io'
        },
        {
          avatar: 'https://avatars.githubusercontent.com/u/49415344?v=4',
          name: 'Blake Edwards',
          title: '',
          excerpt: 'Blake is senior undergraduate student studying computer science and mathematics at the University of South Carolina Honors College. He is also a the founder of Resonant Capital LLC,  a software company.',
          icon: 'mdi-account-multiple',
          alt: 'LinkedIn',
          link: 'https://www.linkedin.com/in/blakete/'
        },
        {
          avatar: 'https://avatars.githubusercontent.com/u/45953643?s=88&u=8e74a5eab08c233f268f19e7fe2ad9a5143add2f&v=4',
          name: 'Brendan Reidy',
          title: '',
          excerpt: 'Brendan is a senior at the University of South Carolina studying Computer Science. Brendan is currently working as a research assistant for the ICAS lab at UofSC. Brendan\'s interests include data science, machine learning, and video game development.',
          icon: 'mdi-account-multiple',
          alt: 'Github',
          link: 'https://github.com/BrendanCReidy'
        },
        {
          avatar: 'https://avatars.githubusercontent.com/u/50686085?v=4',
          name: 'Brendan Curran',
          title: '',
          excerpt: 'Brendan is from Odenton, Maryland and is a Computer Science major at the University of South Carolina. Following graduation Brendan will commission as an officer into the United States Army as a Second Lieutenant in the Cyber Corps.',
          icon: 'mdi-account-multiple',
          alt: 'Github',
          link: 'https://github.com/brendan-curran'
        },
        {
          avatar: 'https://avatars.githubusercontent.com/u/69882301?v=4',
          name: 'Will W Almond',
          title: '',
          excerpt: 'Will is a South Carolina native from the town of Clover. He is currently a Computer Engineering major at the University of South Carolina.',
          icon: 'mdi-account-multiple',
          alt: 'Github',
          link: 'https://github.com/cwalmond'
        },
      ],
      lorem: 'Lorem ipsum dolor sit amet, at aliquam vivendum vel, everti delicatissimi cu eos. Dico iuvaret debitis mel an, et cum zril menandri. Eum in consul legimus accusam. Ea dico abhorreant duo, quo illum minimum incorrupte no, nostro voluptaria sea eu. Suas eligendi ius at, at nemore equidem est. Sed in error hendrerit, in consul constituam cum.',
    }),
  }
</script>

<style>
  #wrapper {
      margin-top: 13%;
  }
  #headerText {
    font-size: 24px;
  }
  #subHeaderText {
    font-size: 18px;
  }
  .v-text-field {
    margin: 0px;
  }

  .v-select {
    margin: 0px;
  }

  .check {
    margin: 10px;
  }
  a:link {
    text-decoration: none;
  }

  .center {
    width: 100%;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .v-btn {
    border-radius: 30px;
    height: 110%;
  }

  
</style>