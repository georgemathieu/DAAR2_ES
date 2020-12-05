<template>
  <div class="hello">
    <div class="large-12 medium-12 small-12 cell">
      <h2>Projet DAAR3 : Spring Boot et ElasticSearch</h2>
      <br>
    </div>
    <div class="large-12 medium-12 small-12 cell">
      <label>CV à upload :
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>
        <button v-on:click="submitFile()">Envoyer</button>
        <br>
    </div>
    <div class="large-12 medium-12 small-12 cell">
      <br>
      <label>Competence à chercher :
        <input type="text" id="text" ref="text" v-on:change="handleSkillSearch()"/>
      </label>
        <button v-on:click="searchSkill()">Rechercher</button>
    </div>
    <div class="large-12 medium-12 small-12 cell">
      <pre style="font-size:80%;">{{ this.display }}</pre>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      file: '',
      text: '',
      display: ''
    }
  },
  methods: {
    handleFileUpload () {
      this.file = this.$refs.file.files[0]
    },
    handleSkillSearch () {
      this.text = this.$refs.text.value
    },
    submitFile () {
      let formData = new FormData()
      formData.append('file', this.file)
      axios
        .post('http://localhost:8080/v1/api/cvs', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(function () {
          console.log('cv upload successful')
        })
        .catch(function () {
          console.log('cv upload failed')
        })
    },
    searchSkill () {
      this.display = ''
      axios
        .get('http://localhost:8080/v1/api/cvs', {
          params: {
            'competence': this.text
          }
        })
        .then(response =>
          (response.data.cv.map(c => (this.display += c.content + '\n ----------------------------------------------------------- \n')))
        )
        .catch(function (res) {
          console.log(res)
        })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
button {
  display: inline-block;
  padding: 0.35em 1.2em;
  border:0.1em solid #FFFFFF;
  margin:0 0.3em 0.3em 0;
  border-radius:0.18em;
  box-sizing: border-box;
  text-decoration:none;
  font-family:'Roboto',sans-serif;
  font-weight:300;
  color:#FFFFFF;
  background-color:#6669E9;
  text-align:center;
  transition: all 0.2s;
}
button:hover{
  color:#FFFFFF;
  background-color:#0B88E7;
}
</style>
