dframe <- read.csv(file = "Roehrentransportsystem.csv",sep=";")
dframe <- dframe[,-length(dframe)]
colnames(dframe)[1] <- NA
dframe[dframe == 0] <- NA



rowTodelete=numeric()
colToDelete<-numeric()
for (v in 1:nrow(dframe)) {
  list1=dframe[v,2:ncol(dframe)]
  if(rowSums(is.na(list1))==118){
    colindex=grep(paste("^",dframe[v,1],"$",sep = ""), colnames(dframe))
    rowTodelete<-c(rowTodelete,v)
    colToDelete<-c(colToDelete,colindex)
    rownames(dframe) <- 1:nrow(dframe)
    #ncol(dframe)=ncol(dframe)-1
    
  }
 
}
rowTodelete
colToDelete

df=dframe[-rowTodelete,]
df2=df[,-colToDelete]


View(df2)

dframe=df2

View(dframe)
for (v in 1:nrow(dframe)) {
  list1=dframe[v,1:ncol(dframe)]
  for (variable in 2:ncol(dframe)) {
    if(!is.na(list1[variable]) && dframe[v,1]!=names(dframe)[variable]){
      
      fileConn<-file("output.txt",open = "a")
      strin=paste(dframe[v,1],names(dframe)[variable])
      write(paste(strin,as.character(list1[variable]),sep = " "),fileConn,append = TRUE)
      close(fileConn)
    }
  }
}



