package edu.washington.cs.knowitall.pattern.eval

import edu.washington.cs.knowitall.common.Resource._
import scopt.OptionParser
import java.io.File
import scala.io.Source
import common.stats.Analysis

object PrecisionYield {
  def main(args: Array[String]) = {
    val parser = new OptionParser("precyield") {
      var scoredFile: File = _

      arg("scored", "scored extractions", { path: String => scoredFile = new File(path) })
    }

    if (parser.parse(args)) {
      val scores = Score.loadScoredFile(parser.scoredFile).sortBy(_.confidence).reverse
      val input = scores.map(scored => (scored.confidence, scored.score))
      
      for ((conf, yld, pr) <- Analysis.precisionYieldMeta(input)) {
        println(conf + "\t" + yld + "\t" + pr)
      }
    }
  }
}