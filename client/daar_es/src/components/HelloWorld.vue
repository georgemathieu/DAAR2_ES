<template>
  <div class="hello">
    <div class="large-12 medium-12 small-12 cell">
      <label>File
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>
        <button v-on:click="submitFile()">Submit</button>
    </div>
    <div class="large-12 medium-12 small-12 cell">
      <label>Competence
        <input type="text" id="text" ref="text" v-on:change="handleSkillSearch()"/>
      </label>
        <button v-on:click="searchSkill()">Recherche</button>
    </div>
    <div class="large-12 medium-12 small-12 cell">
      <p style="font-size:80%;">{{ this.display }}</p>
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
      axios
        .get('http://localhost:8080/v1/api/cvs', {
          params: {
            'competence': this.text
          }
        })
        .then(response =>
          (this.display = response.data.cv)
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
</style>
